package com.mvcoder.buglydemotest.paging;

public interface StudentRepository {

    Listing<StudentBean> getStudentList(int pageSize);

}
