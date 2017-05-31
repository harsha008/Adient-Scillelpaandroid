package lt.sciil.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

import lt.sciil.eLPA.database.model.User;

public class LoginResponse {
    @SerializedName("Message")
    private String message;
    @SerializedName("ResultCode")
    private ResultCode resultCode;
    @SerializedName("Result1")
    private User userData;
    @SerializedName("Result2")
    private ModuleData moduleData;
    @SerializedName("Result3")
    private String Result3;

    public LoginResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public ModuleData getModuleData() {
        return moduleData;
    }

    public void setModuleData(ModuleData moduleData) {
        this.moduleData = moduleData;
    }

    public String getResult3() {
        return Result3;
    }

    public void setResult3(String result3) {
        Result3 = result3;
    }

    public class ModuleData {
        @SerializedName("IDModule")
        private String moduleId;
        @SerializedName("ModuleDesc")
        private String moduleDesc;
        @SerializedName("ModuleID")
        private String moduleName;

        public ModuleData() {
        }

        public String getModuleId() {
            return moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public String getModuleDesc() {
            return moduleDesc;
        }

        public void setModuleDesc(String moduleDesc) {
            this.moduleDesc = moduleDesc;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }
    }

}
