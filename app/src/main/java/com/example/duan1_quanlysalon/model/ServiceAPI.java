package com.example.duan1_quanlysalon.model;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("GetListServiceChose.php")
    Observable<ArrayList<Service>> getListServiceChose(@Query("idBill") int idBill);

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

    @GET("GetListProductChose.php")
    Observable<ArrayList<Product>> getListProductChose(@Query("idBill") int idBill);

    @POST("UpdateListProductChose.php")
    Observable<Boolean> updateListProductChose(@Body ArrayList<Integer> listIdProduct);

    @POST("AddProduct.php")
    Observable<Boolean> addProduct(@Body Product product);

    @POST("UpdateProduct.php")
    Observable<Boolean> updateProduct(@Body Product product);

    @GET("DeleteProduct.php" + "/{idProduct}")
    Observable<Boolean> deleteProduct(@Query("idProduct") int idProduct);

    // bill

    @GET("GetListBill.php")
    Observable<ArrayList<Bill>> getListBill(@Query("status") String status, @Query("date") String date);

    @GET("SetStatusBill.php")
    Observable<Boolean> setStatusBill(@Query("idBill") int idBill, @Query("status") String status);

    @GET("UpdateTotalBill.php")
    Observable<Boolean> updateTotalBill(@Query("idBill") int idBill, @Query("totalPrice") int totalPrice);

    @POST("CreateNewBooking.php")
    Observable<Boolean> CreateNewBooking(@Body Bill bill);

    @POST("UpdateBill.php")
    Observable<Boolean> updateBill(@Body Bill bill);

    @GET("DeleteBill.php" + "/{idBill}")
    Observable<Boolean> deleteBill(@Query("idBill") String idBill);

    @GET("GetBookingAPI.php"+"/{phoneNumberCustomer}")
    Observable<Integer> getBookingAPI(@Query("phoneNumberCustomer") String phoneNumberCustomer);

    @GET("GetList2.php")
    Observable<ArrayList<Bill>> getListBill2(@Query("status") String status, @Query("userNameEmployee") String userNameEmployee, @Query("date") String date);

    @GET("LayIDBillVuaDuocThem.php")
    Observable<Integer> layIDBillVuaDuocThem(@Query("phoneNumberCustomer") String phoneNumberCustomer);

    @GET("GetListBillTheoThang.php")
    Observable<ArrayList<Bill>> getListBillTheoThang(@Query("month") int month, @Query("year") int year);

    //detail

    @POST("AddServiceDetail.php")
    Observable<Boolean> addServiceDetail(@Body ServiceDetail serviceDetail);

    @POST("AddProductDetail.php")
    Observable<Boolean> addProductDetail(@Body ProductDetail productDetail);

    @GET("DeleteDetailBooking.php")
    Observable<Boolean> deleteDetailBookingAPI(@Query("idBill") int idBill);

    @GET("GetDetailService.php")
    Observable<ArrayList<ServiceDetail>> getDetailService(@Query("idBill") int idBill);

    @GET("GetDetailProduct.php")
    Observable<ArrayList<ProductDetail>> getDetailProduct(@Query("idBill") int idBill);




    // employee

    @GET("GetListEmployee.php")
    Observable<ArrayList<Employee>> getListEmployee();

    @GET("GetDetailEmployee.php")
    Observable<String> getDetailEmployee(@Query("userNameEmployee") String userName);

    @POST("UpdateEmployee.php")
    Observable<Boolean> updateEmployee(@Body Employee employee);

    @GET("DeleteEmployee.php")
    Observable<Boolean> deleteEmployee(@Query("userNameEmployee") String userName);

    @POST("AddEmployee.php")
    Observable<Boolean> addEmployee(@Body Employee employee);

    @POST("changePassWord.php")
    Observable<Boolean> changePassWord(@Body Employee employee);

    @POST("GetSalary.php" + "/{userName}")
    Observable<Integer> getSalary(@Query("userName") String userName);

    // login customer

    @POST("loginCustomer.php")
    Observable<Customer> verifyCustomer(@Body Customer customer);

    @GET("CheckExistBooking.php" + "/{phoneNumber}")
    Observable<Bill> checkExistBooking(@Query("phoneNumber") String phoneNumber);

    @GET("CheckExistCustomer.php")
    Observable<Boolean> checkExistCustomer(@Query("phoneNumberCustomer") String phoneNumberCustomer);

    @GET("CreateNewCustomer.php")
    Observable<Boolean> createNewCustomer(@Query("phoneNumberCustomer") String phoneNumberCustomer, @Query("name") String name);

    //img


}
