package com.itheima.case2.vo;

import java.util.List;
/*
    分页bean，这里使用自定义泛型类，这样可以适合于其他类型不仅仅是User,例如Role(角色) Permission(权限)
 */
public class PageBean<T> {
    //1.定义成员变量保存页码上的分页数据
    private List<T> list;
    //2.定义变量保存当前页码
    private int curPage;
    //3.定义成员变量保存每页显示条数
    private int pageSize;
    //4.定义成员变量保存总记录数
    private int count;
    //定义成员方法计算起始索引
    public int getStartIndex(){
        int startIndex=(curPage-1)*pageSize;
        //返回起始索引
        return startIndex;
    }
    //get set


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
