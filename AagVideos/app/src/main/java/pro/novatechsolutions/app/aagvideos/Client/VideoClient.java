package pro.novatechsolutions.app.aagvideos.Client;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pro.novatechsolutions.app.aagvideos.Entities.Video;

public class VideoClient extends AsyncHttpClient {

    private OnServiceResponseListener<Object> mCallBack;
    private final static String MODULE = "videos";

    public VideoClient(OnServiceResponseListener mCallBack ) {
        this.mCallBack = mCallBack;
        addHeader("X-Client-Id", ApiConfiguration.CLIENT_ID);
        addHeader("X-Auth-Token", ApiConfiguration.AUTH);

    }

    public void fetchVideos() {
        String endpoint = ApiConfiguration.ENDPOINT + MODULE;

        get(endpoint, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline
                ArrayList<Video> videos = new ArrayList<>();
                try {
                    JSONArray data = response.getJSONArray("data");
                    for(int i =0; i < data.length(); i++ ){
                        JSONObject object = data.getJSONObject(i);
                        Video video = new Video();
                        video.setAsset_url(object.optString("asset_url"));
                        video.setDescription(object.optString("description"));
                        video.setId(object.getInt("id"));
                        video.setPoster_url(object.optString("poster_url"));
                        video.setTitle(object.optString("title"));
                        videos.add(video);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mCallBack.onSuccess(videos);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                mCallBack.onFailure(new ClientException(t));
            }
        });

    }
}
