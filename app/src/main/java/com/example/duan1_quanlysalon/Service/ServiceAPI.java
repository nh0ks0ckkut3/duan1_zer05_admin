package com.example.duan1_quanlysalon.Service;
import io.reactivex.Observable;
import com.example.duan1_quanlysalon.model.Model_Channel;
import retrofit2.http.GET;
public interface ServiceAPI {
    String BASE_SERVICE = "https://apis.dinhnt.com/";

    @GET("edu.json")
    Observable<Model_Channel> getChannel();
}
