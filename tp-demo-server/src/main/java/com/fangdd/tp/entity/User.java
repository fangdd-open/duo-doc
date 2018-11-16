package com.fangdd.tp.entity;

import com.fangdd.traffic.common.mongo.annotation.AutoIncrement;

import java.util.List;

/**
 * @auth ycoe
 * @date 18/11/28
 */
public class User {
    /**
     * 用户ID
     */
     @AutoIncrement(start = 1000)
    private Long id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 所属团队
     */
    private List<String> teams;

    /**
     * 文档所有者（管理员）
     */
    private List<String> docsOwner;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 用户token，随机生成！
     */
    private String token;

    /**
     * token过期时间
     */
    private Long tokenExpired;

    /**
     * 用户状态： -1已删除/停用 1正常
     */
    private Integer status;

    /**
     * 使用的授权系统账号代码
     */
    private String loginCode;

    /**
     * 最后一次登录时间
     */
    private Long lastLoginTime;

    /**
     * 账号创建时间
     */
    private Long createTime;

    /**
     * 绑定的三方账号
     */
    private List<AuthInfo> auths;

    /**
     * 当前团队
     */
    private Team currentTeam;

    /**
     * 用户可用的登录授权服务代码s
     */
    private List<String> authCodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public List<String> getDocsOwner() {
        return docsOwner;
    }

    public void setDocsOwner(List<String> docsOwner) {
        this.docsOwner = docsOwner;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(Long tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public List<AuthInfo> getAuths() {
        return auths;
    }

    public void setAuths(List<AuthInfo> auths) {
        this.auths = auths;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public List<String> getAuthCodes() {
        return authCodes;
    }

    public void setAuthCodes(List<String> authCodes) {
        this.authCodes = authCodes;
    }
}
