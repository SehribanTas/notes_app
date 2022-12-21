package com.example.note;

public class Tasks {
    private  int task_id;
    private String task;
    private String dayOfMonth;
    private String month;
    private String year;
    private String hourOfDay;
    private String minute;


    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(String hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }


    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }




    public Tasks(int task_id,String task){
        this.task_id=task_id;
        this.task=task;

    }

    public Tasks(int task_id,String task,String dayOfMonth,String month,String year,String hourOfDay,String minute){
            this.task_id=task_id;
            this.task =task;
            this.dayOfMonth=dayOfMonth;
            this.month=month;
            this.hourOfDay=hourOfDay;
            this.minute=minute;
            this.year=year;
    }
}
