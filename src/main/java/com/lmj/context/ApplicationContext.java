package com.lmj.context;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 13:55 2019-08-07
 * 这部分沿用spring的接口规范
 **/
public interface ApplicationContext {

    /**
     * Return the unique id of this application context.
     *
     * @return the unique id of the context, or {@code null} if none
     */
    String getId();

    /**
     * Return a name for the deployed application that this context belongs to.
     *
     * @return a name for the deployed application, or the empty String by default
     */
    String getApplicationName();

    /**
     * Return a friendly name for this context.
     *
     * @return a display name for this context (never {@code null})
     */
    String getDisplayName();

    /**
     * Return the timestamp when this context was first loaded.
     *
     * @return the timestamp (ms) when this context was first loaded
     */
    long getStartupDate();

    /**
     * Return the parent context, or {@code null} if there is no parent
     * and this is the root of the context hierarchy.
     *
     * @return the parent context, or {@code null} if there is no parent
     */
    ApplicationContext getParent();

}
