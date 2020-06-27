package com.example.jyoti.myproject.API_classes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface APIService {

    @FormUrlEncoded
    @POST("mail.php")
    Call<MailClass> getEmailDetails(@Field("type") String type,
                                @Field("email") String email,
                                @Field("name") String name,

                                @Field("subject") String subject,
                                 @Field("message") String message);

    @FormUrlEncoded
    @POST("mail.php")
    Call<MailClass> getEnquiryDetails(@Field("type") String type,
                                    @Field("email") String email,
                                    @Field("name") String name,
                                      @Field("number") String number,
                                    @Field("subject") String subject,
                                    @Field("message") String message);


/*
    @FormUrlEncoded
    @POST("api/request/getStudent")
    Call<StudentDetails> getStudentbyparentid(@Field("key") String key,
                                              @Field("parent_id") int parent_id);

    @FormUrlEncoded
    @POST("api/request/getNotices")
    Call<NoticeDetails> getNoticeForStudent(@Field("key") String key,
                                            @Field("user_id") int user_id, @Field("user_type") int userType, @Field("student_id") String student_id);

    @FormUrlEncoded
    @POST("api/request/getEventParent")
    Call<Events> getEventParent(@Field("key") String key);

    @FormUrlEncoded
    @POST("api/request/parentPermission")
    Call<EventPermission> setparentPermission(@Field("key") String key, @Field("event_id") String event_id
            , @Field("student_id") String student_id, @Field("permission") String permission);

    @FormUrlEncoded
    @POST("api/request/getSubjects")
    Call<SubjectsDetails> getSubjects(@Field("key") String key, @Field("user_id") String user_id
            , @Field("student_id") String student_id);

    @FormUrlEncoded
    @POST("api/request/getHomeWorkBySubIDandDate")
    Call<HomeworkDetails> getHomeWorkBySubIDandDate(@Field("key") String key, @Field("date") String date
            , @Field("student_id") String student_id, @Field("subject_id") String subject_id);

    @FormUrlEncoded
    @POST("api/request/getHolidaysParent")
    Call<HolidayDetails> getHolidaysParent(@Field("key") String key, @Field("parent_id") String parent_id);


    @GET("uploads/image/{filename}")
    @Streaming
    Call<ResponseBody> downloadFile(@Path("filename") String filename);

    @FormUrlEncoded
    @POST("api/request/getAllTestofStudent")
    Call<TestDetails> getAllTestofStudent(@Field("key") String key, @Field("student_id") String student_id);
*/

}