package com.xxin.task;

import com.xxin.dao.TaskDao;
import com.xxin.entity.Attachment;
import com.xxin.entity.Mail;
import com.xxin.entity.Send;
import com.xxin.entity.Task;
import com.xxin.service.SendService;
import com.xxin.utils.DBUtils;
import com.xxin.utils.Util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerTask {
    private List<Send> sends;
    public  void timerSend(){
        Runnable runnable = new Runnable() {
            public void run() {
                Object o = DBUtils.doSql("select * from send where status not in (?,?,?)",Send.class,"sended","sending","timing");
                if (o!=null&&((List<Send>) o).size()>0){
                    sends = (List<Send>) o;
                    SendService sendService = new SendService();
                    for (Send send:sends){
                        Mail mail = sendService.getMail(send.getMid());
                        Task task = new Task();
                        List<Attachment>attachments = sendService.getAttachment(send.getMid());
                        String[] attach;
                        if (attachments!=null){
                            attach = new String[attachments.size()];
                            for (int i=0;i<attachments.size();i++){
                                attach[i] = attachments.get(0).getPath();
                            }
                        }else {
                            attach = null;
                        }
                        Date at = Util.string2Date(send.getStatus());
                        System.out.println("设置定时任务"+send+"at"+at);
                        if (at!=null){
                            send.setStatus("timing");
                            sendService.saveSend(send);
                            TaskDao taskDao = new TaskDao();
                            task.setCreate(new Timestamp(System.currentTimeMillis()));
                            task.setSid(send.getSid());
                            task.setStatus("ready");
                            task.setAt(new Timestamp(at.getTime()));
                            task.setTid(UUID.randomUUID().toString());
                            taskDao.save(task);
                            Timer timer = new Timer();
                            timer .schedule(new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    System.out.println("执行定时");
                                    sendService.send(mail, send, attach, null);
                                    System.out.println("执行结束");
                                    task.setStatus("finished");
                                    taskDao.save(task);
                                }
                            }, at);
                        }
                    }
                }
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);
    }

}
