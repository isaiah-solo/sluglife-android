package slugapp.com.sluglife.http;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import slugapp.com.sluglife.interfaces.HttpCallback;
import slugapp.com.sluglife.models.EventObject;
import slugapp.com.sluglife.models.EventListWrapper;

/**
 * Created by simba on 8/1/15
 * Edited by isaiah on 9/1/2015
 * <p/>
 * This file contains a test http request that gathers event information.
 */
public class TestEventListHttpRequest extends EventListHttpRequest {
    private final static String full = "[{\"name\":\"Edge of Eden\",\"date\":\"July 18th 2015 10pm 2am\",\"summary\":\"Musical festival with a finale by Squidward Tortellini\",\"image\":\"http://www.preparingu.com/wiki/images/d/d6/Burning_Bush_flier_A.jpg\"},{\"name\":\"Holi Festival\",\"date\":\"October 25th 2016 10am 12pm\",\"summary\":\"This event is super fun and super great!\",\"image\":\"http://www.taradarcydesigns.com/images/portfolio/fliers/farmers-market-flier2012legal.jpg\"},{\"name\":\"Meeting\",\"date\":\"September 21st 2016 2pm 4pm\",\"summary\":\"Simba is asking you to go to this super boring meeting thing again\",\"image\":\"http://bcskateboardingalliance.org/wordpress/wp-content/uploads/2011/03/BCSA-Diego-Flier1-600x927.jpg\"},{\"name\":\"French Fried\",\"date\":\"January 1st 2016 13am 7pm\",\"summary\":\"TBH Im not entirely sure what this is, so please dont come to this event at all, This desciption is purposely awkwardly long to hopefully break all of your apps becase I am a devious motherfucker like that. So I am still writing random shit now to break your apps in my malicious ways. I wonder if anyone will actually handle this case. I sure as hell wouldnt. Who the fuck actually spends the hour to write this long a summary about a dumb event called French Fried holy fuck. Btw I hope you arent actually reading this when you should be coding the solution to fixing your summary box that just broke due to my malicious test script. Unless of course it worked in which case, you should probably stop reading this anyway because holy fuck this is a long motha fucking summary. It is called French Fried. Starts at 13am. Be there.\",\"image\":\"http://pre07.deviantart.net/a682/th/pre/f/2013/132/2/4/butter_rebellion_flier_by_sharkynobarky-d63wewa.png\"},{\"name\":\"Short but sweet\",\"date\":\"never\",\"summary\":\"meow\",\"image\":\"https://www.designmaz.net/wp-content/uploads/2014/11/psd-event-flyer-templates.jpg\"}]";

    private final static String normal = "[{\"name\":\"Edge of Eden\",\"date\":\"July 18th 2016 10pm 2am\",\"summary\":\"Musical festival with a finale by Squidward Tortellini\",\"image\":\"http://2.bp.blogspot.com/-Vpbbu8cLkrc/UJ51LEYbb-I/AAAAAAAAd_I/wW0fVbVYILs/s640/banana+slug+Ariolimax+columbianus+4.jpg\"}]";
    private final static String missingimage = "[{\"name\":\"Edge of Eden\",\"date\":\"July 18th 2016 10pm 2am\",\"summary\":\"Musical festival with a finale by Squidward Tortellini\"}]";
    private final static String wrongimage = "[{\"name\":\"Edge of Eden\",\"date\":\"July 18th 2016 10pm 2am\",\"summary\":\"Musical festival with a finale by Squidward Tortellini\",\"image\":\"blah\"}]";
    private final static String longName = "[{\"name\":\"Blahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblah\",\"date\":\"July 18th 2016 10pm 2am\",\"summary\":\"Musical festival with a finale by Squidward Tortellini\",\"image\":\"http://2.bp.blogspot.com/-Vpbbu8cLkrc/UJ51LEYbb-I/AAAAAAAAd_I/wW0fVbVYILs/s640/banana+slug+Ariolimax+columbianus+4.jpg\"}]";
    private final static String longDescription = "[{\"name\":\"Edge of Eden\",\"date\":\"July 18th 2016 10pm 2am\",\"summary\":\"Blahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahlblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblahblah\",\"image\":\"http://2.bp.blogspot.com/-Vpbbu8cLkrc/UJ51LEYbb-I/AAAAAAAAd_I/wW0fVbVYILs/s640/banana+slug+Ariolimax+columbianus+4.jpg\"}]";
    private final static String incorrectDateFormat = "[{\"name\":\"Edge of Eden\",\"date\":\"blah\",\"summary\":\"Musical festival with a finale by Squidward Tortellini\",\"image\":\"http://2.bp.blogspot.com/-Vpbbu8cLkrc/UJ51LEYbb-I/AAAAAAAAd_I/wW0fVbVYILs/s640/banana+slug+Ariolimax+columbianus+4.jpg\"}]";
    private final static String noEvents = "[]";
    private final static String emptyString = "";

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public TestEventListHttpRequest(Context context) {
        super(context);
    }

    /**
     * Executes http request
     *
     * @param callback Http callback
     */
    @Override
    public void execute(HttpCallback<List<EventObject>> callback) {
        try {
            JSONArray arr = new JSONArray(full);
            callback.onSuccess(new EventListWrapper(this.mContext, arr));
        } catch (JSONException je) {
            callback.onError(je);
        }
    }
}
