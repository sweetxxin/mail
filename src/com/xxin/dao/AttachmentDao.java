package com.xxin.dao;

import com.xxin.entity.Attachment;
import com.xxin.utils.DBUtils;

import java.util.List;

public class AttachmentDao {
    public int save(Attachment attachment){
        return DBUtils.save(attachment);
    }
    public List<Attachment>getAttachmentByMid(String mid){
        Object o = DBUtils.query(null, "mid", mid, Attachment.class);
        if (o!=null){
            return (List<Attachment>) o;
        }
        return null;
    }
    public int deleteAttachmentByRid(String aid){
        String[] key = {"aid"};
        String[] val = {aid};
        return DBUtils.deleteBy(key,val,Attachment.class);
    }
}
