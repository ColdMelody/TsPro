package network;

import bean.GlInfoBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Bill on 2017/4/25.
 * Email androidBaoCP@163.com
 */

public interface GlInfo {
    @GET("users/{nameId}")
    Call<GlInfoBean> getGlInfo(@Path("nameId") String nameId);
}
