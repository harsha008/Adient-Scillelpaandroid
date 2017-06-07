package lt.adient.mobility.eLPA.ws.RetrofitModel;

import java.util.List;

import lt.adient.mobility.eLPA.database.model.Answer;
import lt.adient.mobility.eLPA.database.model.Chapter;
import lt.adient.mobility.eLPA.database.model.Question;
import lt.adient.mobility.eLPA.database.model.User;
import lt.adient.mobility.eLPA.database.model.Workstation;

public class AuditQuestionResponse {
    private String Message;
    private int ResultCode;
    private AuditQuestionResponseItem Result1;

    public AuditQuestionResponse(){}

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public AuditQuestionResponseItem getResult1() {
        return Result1;
    }

    public void setResult1(AuditQuestionResponseItem result1) {
        Result1 = result1;
    }

    public class AuditQuestionResponseItem{
        List<AuditQuestion> AuditQuestions;
        int AuditStatus;
        String IDLPAAudit;
        int Layer;
        Workstation Machine;
        String Planned;
        String Started;
        User StartedBy;
        User User;

        public AuditQuestionResponseItem(){}

        public List<AuditQuestion> getAuditQuestions() {
            return AuditQuestions;
        }

        public void setAuditQuestions(List<AuditQuestion> auditQuestions) {
            AuditQuestions = auditQuestions;
        }

        public int getAuditStatus() {
            return AuditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            AuditStatus = auditStatus;
        }

        public String getIDLPAAudit() {
            return IDLPAAudit;
        }

        public void setIDLPAAudit(String IDLPAAudit) {
            this.IDLPAAudit = IDLPAAudit;
        }

        public int getLayer() {
            return Layer;
        }

        public void setLayer(int layer) {
            Layer = layer;
        }

        public Workstation getMachine() {
            return Machine;
        }

        public void setMachine(Workstation machine) {
            Machine = machine;
        }

        public String getPlanned() {
            return Planned;
        }

        public void setPlanned(String planned) {
            Planned = planned;
        }

        public String getStarted() {
            return Started;
        }

        public void setStarted(String started) {
            Started = started;
        }

        public lt.adient.mobility.eLPA.database.model.User getStartedBy() {
            return StartedBy;
        }

        public void setStartedBy(lt.adient.mobility.eLPA.database.model.User startedBy) {
            StartedBy = startedBy;
        }

        public lt.adient.mobility.eLPA.database.model.User getUser() {
            return User;
        }

        public void setUser(lt.adient.mobility.eLPA.database.model.User user) {
            User = user;
        }

        public class AuditQuestion{
            Chapter Chapter;
            List<QuestionItem> Questions;

            public AuditQuestion(){}

            public Chapter getChapter() {
                return Chapter;
            }

            public void setChapter(Chapter chapter) {
                Chapter = chapter;
            }

            public List<QuestionItem> getQuestions() {
                return Questions;
            }

            public void setQuestions(List<QuestionItem> questions) {
                Questions = questions;
            }


            public class QuestionItem{
                Answer Answer;
                Question Question;

                public QuestionItem(){}

                public Answer getAnswer() {
                    return Answer;
                }

                public void setAnswer(Answer answer) {
                    Answer = answer;
                }

                public lt.adient.mobility.eLPA.database.model.Question getQuestion() {
                    return Question;
                }

                public void setQuestion(lt.adient.mobility.eLPA.database.model.Question question) {
                    Question = question;
                }

            }
        }
    }
}
