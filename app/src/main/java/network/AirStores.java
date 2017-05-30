package network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Bill on 2017/4/25.
 * Email androidBaoCP@163.com
 */

public interface AirStores {
    @GET("adat/sk/{dityId}/html")
    Call<ResponseBody> getWeather(@Query("cityId") String city);
}
