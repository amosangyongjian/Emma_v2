package com.example.amosang.emma_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amosang on 21/03/16.
 */
public class RequestAdapter extends ArrayAdapter{

    ArrayList<UserReq> userReqs = new ArrayList<>();

    public RequestAdapter(Context context, int resource) {
        super(context, resource);
    }
    static class DataHandler{
        TextView requestTitle;
        TextView requestDate;
    }

    public void add(UserReq req){
        super.add(req);
        userReqs.add(req);
    }

    public UserReq getRequest(int position){
        return this.userReqs.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DataHandler dh;
        if(convertView==null){

            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.request_listadapter,parent,false);
            dh=new DataHandler();
            dh.requestTitle = (TextView)row.findViewById(R.id.reqTitle);
            dh.requestDate = (TextView)row.findViewById(R.id.reqDate);
            row.setTag(dh);
        }else{
            dh=(DataHandler)row.getTag();
        }
        UserReq ur = (UserReq)this.getItem(position);
        dh.requestTitle.setText(ur.getRequestTitle());
        dh.requestDate.setText(String.valueOf(ur.getRequestDate()));
        return row;
    }
}
