package org.iotivity.simpleserver;

import android.util.Log;

import org.iotivity.OCMain;
import org.iotivity.OCRepresentation;
import org.iotivity.OCRequest;
import org.iotivity.OCRequestHandler;
import org.iotivity.OCStatus;

public class PostLightRequestHandler implements OCRequestHandler {

    private static final String TAG = PostLightRequestHandler.class.getSimpleName();

    private ServerActivity activity;

    public PostLightRequestHandler(ServerActivity activity) {
        this.activity = activity;
    }

    @Override
    public void handler(OCRequest request, int interfaces, Object userData) {
        Log.d(TAG, "inside Post Light Request Handler");

        Light light = (Light) userData;
        activity.msg("Post Light:");

        OCRepresentation rep = request.getRequest_payload();
        while (rep != null) {
            activity.msg("\tkey: " + rep.getName() + ", type: " + rep.getType());
            switch (rep.getType()) {
                case OC_REP_BOOL:
                    light.state = rep.getValue().getBool();
                    activity.msg("\t\tvalue: " + light.state);
                    break;
                case OC_REP_INT:
                    light.power = rep.getValue().getInteger();
                    activity.msg("\t\tvalue: " + light.power);
                    break;
                case OC_REP_STRING:
                    light.name = rep.getValue().getString();
                    activity.msg("\t\tvalue: " + light.name);
                    break;
                default:
                    activity.msg("NOT YET HANDLED VALUE");
                    OCMain.sendResponse(request, OCStatus.OC_STATUS_BAD_REQUEST);
            }
            rep = rep.getNext();
        }

        activity.printLine();
        OCMain.sendResponse(request, OCStatus.OC_STATUS_CHANGED);
    }
}