package com.mvcoder.buglydemotest.paging;

public interface StudentRepository {

    StudentListing<StudentBean> getStudentList(int pageSize);

}
