<#assign title="${artifact.name!artifact.artifactId}-接口文档"/>
<#include "./common/header.ftl" />
<div class="book">
    <div class="book-summary">
        <div id="book-search-input" role="search">
            <input type="text" placeholder="Type to search" />
        </div>
        <nav role="navigation">
            <ul class="summary">
                <#include "./doc.index.ftl" />

                <li class="divider"></li>
                <li>
                    <a href="https://m.fangdd.com" target="blank" class="gitbook-link">自动化文档 by @徐文振</a>
                </li>
            </ul>
        </nav>
    </div>

    <div class="book-body">
        <div class="body-inner">
            <div class="book-header" role="navigation">
                <h1>
                    <i class="fa fa-circle-o-notch fa-spin"></i>
                    <a href="/index.html" >返回文档索引</a>
                </h1>
            </div>

            <div class="page-wrapper" tabindex="-1" role="main">
                <div class="page-inner">
                    <div id="book-search-results">
                        <div class="search-noresults">
                            <section class="normal markdown-section">
                                <#if api??>
                                    <#if api.type == 0>
                                        <#include "./doc.rest.ftl" />
                                    <#else >
                                        <#include "./doc.dubbo.ftl" />
                                    </#if>
                                <#else >
                                    <ul>
                                        <#include "./doc.index.ftl" />
                                    </ul>
                                </#if>
                            </section>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        var gitbook = gitbook || [];
        gitbook.push(function() {
            gitbook.page.hasChanged({
                "page": {
                    "title": "Introduction",
                    "level": "1.1",
                    "depth": 1,
                    "next": {
                    },
                    "dir": "ltr"
                },
                "config": {
                    "gitbook": "*",
                    "theme": "default",
                    "variables": {},
                    "plugins": [],
                    "pluginsConfig": {
                        "highlight": {},
                        "search": {},
                        "lunr": {"maxIndexSize": 1000000, "ignoreSpecialCharacters": false},
                        "sharing": {

                        },
                        "fontsettings": {"theme": "white", "family": "sans", "size": 2},
                        "theme-default": {
                            "styles": {
                                "website": "styles/website.css",
                                "pdf": "styles/pdf.css",
                                "epub": "styles/epub.css",
                                "mobi": "styles/mobi.css",
                                "ebook": "styles/ebook.css",
                                "print": "styles/print.css"
                            }, "showLevel": false
                        }
                    },
                    "structure": {
                        "langs": "LANGS.md",
                        "readme": "README.md",
                        "glossary": "GLOSSARY.md",
                        "summary": "SUMMARY.md"
                    },
                    "pdf": {
                        "pageNumbers": true,
                        "fontSize": 12,
                        "fontFamily": "Arial",
                        "paperSize": "a4",
                        "chapterMark": "pagebreak",
                        "pageBreaksBefore": "/",
                        "margin": {"right": 62, "left": 62, "top": 56, "bottom": 56}
                    },
                    "styles": {
                        "website": "styles/website.css",
                        "pdf": "styles/pdf.css",
                        "epub": "styles/epub.css",
                        "mobi": "styles/mobi.css",
                        "ebook": "styles/ebook.css",
                        "print": "styles/print.css"
                    }
                },
                "file": {"path": "README.md", "mtime": "2017-08-23T10:53:13.000Z", "type": "markdown"},
                "gitbook": {"version": "3.2.2", "time": "2017-08-23T10:53:15.593Z"},
                "basePath": ".",
                "book": {"language": ""}
            });
        });
    </script>
<#include "./common/footer.ftl" />