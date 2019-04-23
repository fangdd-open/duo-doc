import subprocess
import sys
import argparse
import logging
import os
from contextlib import contextmanager


def print_banner():
    logging.info(r"""
#       ####### #     # ####### #             #      #       #######  #####     #    #           #####   #####  ######  ### ######  #######
#       #       #     # #       #            ##      #       #     # #     #   # #   #          #     # #     # #     #  #  #     #    #
#       #       #     # #       #           # #      #       #     # #        #   #  #          #       #       #     #  #  #     #    #
#       #####   #     # #####   #             #      #       #     # #       #     # #           #####  #       ######   #  ######     #
#       #        #   #  #       #             #      #       #     # #       ####### #                # #       #   #    #  #          #
#       #         # #   #       #             #      #       #     # #     # #     # #          #     # #     # #    #   #  #          #
####### #######    #    ####### #######     #####    ####### #######  #####  #     # #######     #####   #####  #     # ### #          #
      Build Script
    """)


def level0_env(env, java_home, java_home_6, java_home_7,
               java_home_8, java_home_9, diamond_host,
               diamond_port, deploy_dir, working_dir,
               conf_dir, data_dir, logs_dir, std_log,
               fdd_java_opts):
    return '''#!/usr/bin/env bash
export FDD_ENV={env}
export JAVA_HOME={java_home}
export JAVA_HOME_6={java_home_6}
export JAVA_HOME_7={java_home_7}
export JAVA_HOME_8={java_home_8}
export JAVA_HOME_9={java_home_9}
export DEPLOY_DIR={deploy_dir}
export DIAMOND_HOST={diamond_host}
export DIAMOND_PORT={diamond_port}
export WORKING_DIR={working_dir}
export CONF_DIR={conf_dir}
export DATA_DIR={data_dir}
export LOGS_DIR={logs_dir}
export STD_LOG={std_log}
export FDD_JAVA_OPTS={fdd_java_opts}
'''.format(env=env, java_home=java_home, java_home_6=java_home_6, java_home_7=java_home_7,
           java_home_8=java_home_8, java_home_9=java_home_9, diamond_host=diamond_host,
           diamond_port=diamond_port, deploy_dir=deploy_dir, working_dir=working_dir,
           conf_dir=conf_dir, data_dir=data_dir, logs_dir=logs_dir, std_log=std_log,
           fdd_java_opts=fdd_java_opts)


def level1_start(app_name, main_class, replace_content):
    return """
#!/usr/bin/env bash
export JAVA_HOME=$JAVA_HOME_8
export PATH=$JAVA_HOME_8/bin:$PATH

declare -i timeOut
timeOut=90

pushd $WORKING_DIR >> /dev/null

function isRunning() {{
    if [ -f "$DATA_DIR/pid.txt" ] ; then
		local pid=`cat $DATA_DIR/pid.txt`
		local running=` jps -v | awk ' {{print $1}} ' | grep $pid | wc -l`
		if [[ "x$running" == "x0" ]] ; then
			echo 0
		else
			echo 1
		fi
	else
		echo 0
	fi
}}

running=$(isRunning)
if [[ "x$running" == "x1" ]] ; then
	echo "{0} is running, fail to start."
	exit 1
else
	echo "{0} is not running, try to start it. Time out $timeOut seconds."
	mkdir -p $LOGS_DIR
	mkdir -p $DATA_DIR
	jvmOpts=""
    if [ -f "$WORKING_DIR/bin/jvmopts" ] ; then
            jvmOpts="$FDD_JAVA_OPTS `$WORKING_DIR/bin/jvmopts`"
    else
            jvmOpts="$FDD_JAVA_OPTS"
    fi
    
    echo "current application user=$USER"
    if [[ "$USER" != "java" ]] ; then
        echo "not java user!"
        {2}
        echo 'replace jvmOpts config!'
    fi
    
    
    echo "jvm opts=$jvmOpts"
    echo "nohup java $jvmOpts -cp $WORKING_DIR/lib/* org.springframework.boot.loader.JarLauncher >> $STD_LOG 2>&1 &"
	nohup java $jvmOpts -cp $WORKING_DIR/lib/* {1} >> $STD_LOG 2>&1 &

	running='0'
	declare -i tryCount
	tryCount=0
	while [ "x$running" != "x1" ] ; do
	    if [ $tryCount -gt $timeOut ] ; then
			echo "time out, failed to start."
			exit 1
			break
		fi

		echo -n "."
		running=$(isRunning)
		sleep 1
		tryCount=$tryCount+1
	done
	echo "done."
fi

popd >> /dev/null""".format(app_name, main_class, replace_content)


def level1_stop(app_name):
    return '''#!/usr/bin/env bash
export JAVA_HOME=$JAVA_HOME_8
export PATH=$JAVA_HOME_8/bin:$PATH

declare -i timeOut
timeOut=30

pushd $WORKING_DIR >> /dev/null

pid=`cat $DATA_DIR/pid.txt`

function isRunning() {{
	if [ -f "$DATA_DIR/pid.txt" ] ; then
		local running=`jps -v | awk ' {{print $1}} ' | grep $pid | wc -l`
		if [[ "x$running" == "x0" ]] ; then
			echo 0
		else
			echo 1
		fi
	else
		echo 0
	fi
}}

running=$(isRunning)
if [[ "x$running" == "x1" ]] ; then
	echo "{0} is running, try to stop."

    pid=`cat $DATA_DIR/pid.txt`
	kill $pid

	running='1'
	declare -i tryCount
	tryCount=0
	while [ "x$running" != "x0" ] ; do
		if [ $tryCount -gt $timeOut ] ; then
			echo "time out, failed to stop."
			popd >> /dev/null
            kill -9 $pid
			exit 0
		fi

		echo -n "."
		running=$(isRunning)
		sleep 1
		tryCount=$tryCount+1
	done
else
	echo "{0} is not running."
fi

echo "done."
popd >> /dev/null

exit 0
'''.format(app_name)


@contextmanager
def cd(path):
    old_dir = os.getcwd()
    os.chdir(path)
    try:
        yield
    finally:
        os.chdir(old_dir)


def main(args):
    with cd(args.basedir):
        try:
            os.mkdir('./bin')
        except Exception as e:
            logging.error(e)
        envf = open('./bin/env.sh', 'wb')
        envf.write(level0_env(args.env, '', '', '', '', '', 'diamond.dc.fdd', 9090, args.basedir, args.basedir,
                              args.basedir + '/conf', args.basedir + '/data', args.basedir + '/logs/',
                              args.basedir + 'std.out', ''))
        startf = open('./bin/start.sh', 'wb')
        deployMetaFile = open('./deployMeta.properties', 'rb')
        deployMeta = {}
        for line in deployMetaFile.readlines():
            deployMeta[line.split("=")[0]] = line.split("=")[1].replace('\n', '').encode('utf-8')
        startf.write(level1_start(args.name, deployMeta.get('mainClass'), 'jvmOpts=${jvmOpts/\/home\/java/"~"}'))

        stopf = open('./bin/stop.sh', 'wb')
        stopf.write(level1_stop(args.name))

        logging.info("print run cmd")
        logging.info('cd {0} && chmod +x ./bin/* && source ./bin/env.sh && ./bin/start.sh'.format(args.basedir))
        subprocess.Popen('cd {0} && chmod +x ./bin/* && source ./bin/env.sh && ./bin/start.sh'.format(args.basedir),
                         shell=True)
        logging.info('start success !')


if __name__ == '__main__':
    LOG_LEVEL = logging.INFO
    if '--debug' in sys.argv[1:]:
        LOG_LEVEL = logging.DEBUG
    log_format = '[%(levelname)s] %(funcName)s: %(message)s'
    logging.basicConfig(level=LOG_LEVEL,
                        format=log_format)

    parser = argparse.ArgumentParser(description='InfluxDB build and packaging script.')
    parser.add_argument('--verbose', '-v', '--debug',
                        action='store_true',
                        help='Use debug output')
    parser.add_argument('--basedir', '-dir',
                        metavar='<base directory>',
                        default='.',
                        type=os.path.abspath,
                        help='Base directory')
    parser.add_argument('--name', '-n',
                        metavar='<name>',
                        default='test',
                        type=str,
                        help='App Name')
    parser.add_argument('--env', '-e',
                        metavar='<name>',
                        default='test',
                        type=str,
                        help='use env')
    args = parser.parse_args()
    print_banner()
    sys.exit(main(args))