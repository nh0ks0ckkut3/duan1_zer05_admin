package com.example.duan1_quanlysalon.model;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceAPI {
    String BASE_Service = "https://apis.dinhnt.com/";
    String BASE_API_ZERO5 = "https://sub1.tridinhne.click/api/";

    // news
    @GET("edu.json")
    Observable<ModelChannel> getChannel();

    // phần đăng nhập
    @POST("login.php")
    Observable<Employee> verify(@Body Employee employee);

    // service

    @GET("GetListService.php")
    Observable<ArrayList<Service>> getListService();

    @GET("GetListServiceChose.php" + "/{idBill}")
    Observable<ArrayList<Service>> getListServiceChose(@Query("idBill") String idBill);

    @POST("UpdateListServiceChose.php")
    Observable<Boolean> updateListServiceChose(@Body ArrayList<Integer> listIdService);

    @POST("AddService.php")
    Observable<Boolean> addService(@Body Service service);

    @POST("UpdateService.php")
    Observable<Boolean> updateService(@Body Service service);

    @GET("DeleteService.php" + "/{idService}")
    Observable<Boolean> deleteService(@Query("idService") int idService);


    // product

    @GET("GetListProduct.php")
    Observable<ArrayList<Product>> getListProduct();

    @GET("GetListProductChose.php" + "/{idBill}")
    Observable<ArrayList<Product>> getListProductChose(@Query("idBill") String idBill);

    @POST("UpdateListProductChose.php")
    Observable<Boolean> updateListProductChose(@Body ArrayList<Integer> listIdProduct);

    @POST("CreateNewProduct.php")
    Observable<Boolean> createNewProduct(@Body Product product);

    @POST("UpdateProduct.php")
    Observable<Boolean> updateProduct(@Body Product service);

    @GET("DeleteProduct.php" + "/{idProduct}")
    Observable<Boolean> deleteProduct(@Query("idProduct") int idProduct);

    // bill

    @GET("GetListBill.php")
    Observable<ArrayList<Bill>> getListBill(@Query("status") String status);

    @GET("SetStatusBill.php" + "/{idBill}" + "/{status}")
    Observable<Boolean> setStatusBill(@Query("idBill") int idBill, @Query("status") String status);

    @POST("CreateNewBooking.php")
    Observable<Boolean> CreateNewBooking(@Body Bill bill);

    @POST("UpdateBill.php")
    Observable<Boolean> updateBill(@Body Bill bill);

    @GET("DeleteBill.php" + "/{idBill}")
    Observable<Boolean> deleteBill(@Query("idBill") String idBill);

    @GET("GetBookingAPI.php"+"/{phoneNumberCustomer}")
    Observable<Integer> getBookingAPI(@Query("phoneNumberCustomer") String phoneNumberCustomer);

    //detail

    @POST("AddServiceDetail.php")
    Observable<Boolean> addServiceDetail(@Body ServiceDetail serviceDetail);

    @POST("AddProductDetail.php")
    Observable<Boolean> addProductDetail(@Body ProductDetail productDetail);

    @GET("DeleteDetailBooking.php")
    Observable<Boolean> deleteDetailBookingAPI(@Query("idBill") int idBill);

    // employee

    @GET("GetListEmployee.php")
    Observable<ArrayList<Employee>> getListEmployee();

    @GET("GetDetailEmployee.php" + "/{userName}")
    Observable<Employee> getDetailEmployee(@Query("userName") String userName);

    @POST("UpdateEmployee.php")
    Observable<Boolean> updateEmployee(@Body Employee employee);

    @GET("DeleteEmployee.php" + "/{userName}")
    Observable<Boolean> deleteEmployee(@Query("userName") String userName);

    @POST("AddEmployee.php")
    Observable<Boolean> addEmployee(@Body Employee employee);

    @POST("ChangePassWord.php")
    Observable<Boolean> changePassWord(@Body Employee employee);

    @POST("GetSalary.php" + "/{userName}")
    Observable<Integer> getSalary(@Query("userName") String userName);

    // login customer

    @POST("loginCustomer.php")
    Observable<Customer> verifyCustomer(@Body Customer customer);

    @GET("CheckExistBooking.php" + "/{phoneNumber}")
    Observable<Bill> checkExistBooking(@Query("phoneNumber") String phoneNumber);

}
