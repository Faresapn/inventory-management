package com.faresa.inventaris_ontrucks.connection;

import com.faresa.inventaris_ontrucks.pojo.JumlahResponse;
import com.faresa.inventaris_ontrucks.pojo.TokenResponse;
import com.faresa.inventaris_ontrucks.pojo.admin.AdminResponse;
import com.faresa.inventaris_ontrucks.pojo.divisi.create.DivisiCreateResponse;
import com.faresa.inventaris_ontrucks.pojo.divisi.delete.DivisiDeleteResponse;
import com.faresa.inventaris_ontrucks.pojo.divisi.get.DivisiGetResponse;
import com.faresa.inventaris_ontrucks.pojo.divisi.update.DivisiUpdateResponse;
import com.faresa.inventaris_ontrucks.pojo.laptop.create.LaptopCreateResponse;
import com.faresa.inventaris_ontrucks.pojo.laptop.delete.LaptopDeleteResponse;
import com.faresa.inventaris_ontrucks.pojo.laptop.get.LaptopGetResponse;
import com.faresa.inventaris_ontrucks.pojo.laptop.update.LaptopUpdateResponse;
import com.faresa.inventaris_ontrucks.pojo.pegawai.create.PegawaiCreateResponse;
import com.faresa.inventaris_ontrucks.pojo.pegawai.delete.PegawaiDeleteResponse;
import com.faresa.inventaris_ontrucks.pojo.pegawai.get.PegawaiGetResponse;
import com.faresa.inventaris_ontrucks.pojo.pegawai.update.PegawaiUpdateResponse;
import com.faresa.inventaris_ontrucks.pojo.printer.get.PrinterGetResponse;
import com.faresa.inventaris_ontrucks.pojo.printer.create.PrinterCreateResponse;
import com.faresa.inventaris_ontrucks.pojo.printer.delete.DeletePrinterResponse;
import com.faresa.inventaris_ontrucks.pojo.printer.update.PrinterUpdateResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {


    @GET("allData")
    Call<JumlahResponse> getAll();

    @FormUrlEncoded
    @POST("admin/login")
    Call<TokenResponse> loginRequest(@Field("name") String name,
                                     @Field("password") String password
    );
    //region PROFILE
    @GET("admin/getAdmin")
    Call<AdminResponse> getUser(@Header("Authorization") String authorization);

    //endregion


    //region CREATE DIVISI
    @FormUrlEncoded
    @POST("divisi")
    Call<DivisiCreateResponse> createDivision(@Header("Authorization") String authorization,
                                              @Field("name") String division
    );

    //endregion

    //region GET DIVISI
    @GET("divisi")
    Call<DivisiGetResponse> getDivision(@Header("Authorization") String authorization);
    //endregion

    //region UPDATE DIVISI
    @FormUrlEncoded
    @POST("divisi/{id}")
    Call<DivisiUpdateResponse> updateDivision(@Header("Authorization") String authorization,
                                              @Field("name") String name,
                                              @Field("_method") String put,
                                              @Path("id") String id);
    //endregion

    //region DELETE DIVISI
    @DELETE("divisi/{id}")
    Call<DivisiDeleteResponse> deleteDivison(@Header("Authorization") String authorization,
                                             @Path("id") int id);
    //endregion


    //region CREATE LAPTOP


    @Multipart
    @POST("laptop")
    Call<LaptopCreateResponse> createLaptop(@Header("Authorization") String authorization,
                                            @Part("type") RequestBody type,
                                            @Part("serial_number") RequestBody serial,
                                            @Part("inventaris_code") RequestBody inventaris,
                                            @Part("operating_system") RequestBody os,
                                            @Part("pegawai_id") RequestBody id,
                                            @PartMap Map<String, RequestBody> map
    );

    @Multipart
    @POST("laptop")
    Call<LaptopCreateResponse> createLaptop2(@Header("Authorization") String authorization,
                                             @Part("type") RequestBody type,
                                             @Part("serial_number") RequestBody serial,
                                             @Part("inventaris_code") RequestBody inventaris,
                                             @Part("operating_system") RequestBody os,
                                             @Part("pegawai_id") RequestBody id
    );

    //endregion

    //region GET LAPTOP
    @GET("laptop")
    Call<LaptopGetResponse> getLaptop(@Header("Authorization") String authorization);
    //endregion


    //region DELETE LAPTOP
    @DELETE("laptop/{id}")
    Call<LaptopDeleteResponse> deleteLaptop(@Header("Authorization") String authorization,
                                            @Path("id") int id,
                                            @Query("file_id") int file
    );
    //endregion

    //region UPDATE LAPTOP
    @Multipart
    @POST("laptop/{id}")
    Call<LaptopUpdateResponse> updateLaptop(@Header("Authorization") String authorization,
                                            @Path("id") String id,
                                            @Part("type") RequestBody type,
                                            @Part("serial_number") RequestBody serial,
                                            @Part("inventaris_code") RequestBody inventaris,
                                            @Part("operating_system") RequestBody os,
                                            @Part("file_id") RequestBody file_id,
                                            @PartMap Map<String, RequestBody> map,
                                            @Part("_method") RequestBody put
    );

    @Multipart
    @POST("laptop/{id}")
    Call<LaptopUpdateResponse> updateLaptop2(@Header("Authorization") String authorization,
                                             @Path("id") String id,
                                             @Part("type") RequestBody type,
                                             @Part("serial_number") RequestBody serial,
                                             @Part("inventaris_code") RequestBody inventaris,
                                             @Part("operating_system") RequestBody os,
                                             @Part("_method") RequestBody put
    );


    //endregion




    @GET("pegawai")
    Call<PegawaiGetResponse> getPegawai(@Header("Authorization") String auth);

    @Multipart
    @POST("pegawai")
    Call<PegawaiCreateResponse> createPegawai(@Header("Authorization") String auth,
                                              @Part("name") RequestBody pegawai,
                                              @Part("divisi_name") RequestBody spinner,
                                              @PartMap Map<String, RequestBody> map
    );


    @DELETE("pegawai/{id}")
    Call<PegawaiDeleteResponse> deletePegawai(@Header("Authorization") String authorization,
                                              @Path("id") int id,
                                              @Query("file_id") int file
    );

    @Multipart
    @POST("pegawai/{pegawai_id}")
    Call<PegawaiUpdateResponse> updatePegawai(@Header("Authorization") String authorization,
                                              @Part("name") RequestBody name,
                                              @Part("_method") RequestBody put,
                                              @Part("divisi_name") RequestBody divisi_name,
                                              @Part("file_id") RequestBody file_id,
                                              @PartMap Map<String, RequestBody> map,

                                              @Path("pegawai_id") String id
    );

    @Multipart
    @POST("pegawai/{pegawai_id}")
    Call<PegawaiUpdateResponse> updatePegawaii(@Header("Authorization") String authorization,
                                               @Part("name") RequestBody name,
                                               @Part("_method") RequestBody put,
                                               @Part("divisi_name") RequestBody divisi_name,
                                               @Path("pegawai_id") String id
    );

    @GET("printer")
    Call<PrinterGetResponse> getPrinter(@Header("Authorization") String authorization);

    //printercreate
    @Multipart
    @POST("printer")
    Call<PrinterCreateResponse> createPrinter(@Header("Authorization") String authorization,
                                              @Part("divisi") RequestBody spinner,
                                              @Part("type") RequestBody type,
                                              @Part("inventaris_code") RequestBody inventaris,
                                              @PartMap Map<String, RequestBody> map
    );

    @Multipart
    @POST("printer")
    Call<PrinterCreateResponse> createPrinterNull(@Header("Authorization") String authorization,
                                                  @Part("divisi") RequestBody spinner,
                                                  @Part("type") RequestBody type,
                                                  @Part("inventaris_code") RequestBody inventaris
    );

    @DELETE("printer/{id}")
    Call<DeletePrinterResponse> deletePrinter(@Header("Authorization") String authorization,
                                              @Path("id") int id,
                                              @Query("file_id") int file
    );

    @Multipart
    @POST("printer/{id}")
    Call<PrinterUpdateResponse> updatePrinter(@Header("Authorization") String authorization,
                                              @Part("divisi") RequestBody spinner,
                                              @Part("type") RequestBody type,
                                              @Part("inventaris_code") RequestBody inventaris,
                                              @PartMap Map<String, RequestBody> map,
                                              @Part("file_id") RequestBody file_id,
                                              @Path("id") String id,
                                              @Part("_method") RequestBody put
    );

    @Multipart
    @POST("printer/{id}")
    Call<PrinterUpdateResponse> updatePrinterPDF(@Header("Authorization") String authorization,
                                                 @Part("divisi") RequestBody spinner,
                                                 @Part("type") RequestBody type,
                                                 @Part("inventaris_code") RequestBody inventaris,
                                                 @Path("id") String id,
                                                 @Part("_method") RequestBody put
    );

}
