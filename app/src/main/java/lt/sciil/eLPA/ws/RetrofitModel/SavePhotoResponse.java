package lt.sciil.eLPA.ws.RetrofitModel;

import com.google.gson.annotations.SerializedName;

public class SavePhotoResponse extends Response<SavePhotoResponse.SavePhotoResult> {

    public class SavePhotoResult {
        @SerializedName("IDDoc")
        private String docId;
        @SerializedName("IDRef")
        private String refId;

        public SavePhotoResult() {
        }

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }
    }
}
