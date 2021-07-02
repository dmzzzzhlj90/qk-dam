package com.qk.dam.sqlloader.vo;

public class DolphinProcessInstanceVO {

    /**
     * process definition code
     */
    private Long process_definition_code;

    /**
     * name
     */
    private String name;
    /**
     * process state
     */
    private int state;

    /**
     * user_name
     */
    private String user_name;


    /**
     * update_time
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String update_time;

    public DolphinProcessInstanceVO(Long process_definition_code, String name, int state, String user_name, String update_time) {
        this.process_definition_code = process_definition_code;
        this.name = name;
        this.state = state;
        this.user_name = user_name;
        this.update_time = update_time;
    }

    public Long getProcess_definition_code() {
        return process_definition_code;
    }

    public void setProcess_definition_code(Long process_definition_code) {
        this.process_definition_code = process_definition_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DolphinProcessInstanceVO)) return false;
        DolphinProcessInstanceVO that = (DolphinProcessInstanceVO) o;
        return getProcess_definition_code().equals(that.getProcess_definition_code()) && getName().equals(that.getName());
    }


}
