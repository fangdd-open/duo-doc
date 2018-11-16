package com.fangdd.tp.core.config.http;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @auth ycoe
 * @date 18/8/23
 */
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;

    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null)
            cacheInputStream();

        return new CachedServletInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private void cacheInputStream() throws IOException {
    /* Cache the inputstream in order to read it multiple times. For
     * convenience, I use apache.commons IOUtils
     */
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    /* An inputstream which reads the cached request body */
    public class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream input;

        public CachedServletInputStream() {
      /* create a new input stream from the cached request body */
            input = new ByteArrayInputStream(cachedBytes.toByteArray());
        }

        /**
         * Has the end of this InputStream been reached?
         *
         * @return <code>true</code> if all the data has been read from the stream,
         * else <code>false</code>
         * @since Servlet 3.1
         */
        @Override
        public boolean isFinished() {
            return true;
        }

        /**
         * Can data be read from this InputStream without blocking?
         * Returns  If this method is called and returns false, the container will
         * invoke {@link ReadListener#onDataAvailable()} when data is available.
         *
         * @return <code>true</code> if data can be read without blocking, else
         * <code>false</code>
         * @since Servlet 3.1
         */
        @Override
        public boolean isReady() {
            return true;
        }

        /**
         * Sets the {@link ReadListener} for this {@link ServletInputStream} and
         * thereby switches to non-blocking IO. It is only valid to switch to
         * non-blocking IO within async processing or HTTP upgrade processing.
         *
         * @param listener The non-blocking IO read listener
         * @throws IllegalStateException If this method is called if neither
         *                               async nor HTTP upgrade is in progress or
         *                               if the {@link ReadListener} has already
         *                               been set
         * @throws NullPointerException  If listener is null
         * @since Servlet 3.1
         */
        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() throws IOException {
            return input.read();
        }
    }
}
