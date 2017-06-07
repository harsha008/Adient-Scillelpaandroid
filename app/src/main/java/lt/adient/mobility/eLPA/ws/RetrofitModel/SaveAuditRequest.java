package lt.adient.mobility.eLPA.ws.RetrofitModel;

import java.util.ArrayList;
import java.util.List;

import lt.adient.mobility.eLPA.database.model.Answer;
import lt.adient.mobility.eLPA.database.model.Chapter;

/**
 * Created by Haroldas on 10/19/2016.
 */

public class SaveAuditRequest {

    private String IDSession = "NOTIMPL";
    private RequestModule Module;
    private RequestUser User;
    private SaveAuditRequestItem Parameter1;

    public SaveAuditRequest() {
    }

    public SaveAuditRequest(String idUser, Long lgeId, String idModule, String auditId, List<Chapter> chapters, List<Answer> answers) {
        this.Module = new RequestModule(idModule);
        this.User = new RequestUser(idUser, lgeId);
        this.Parameter1 = new SaveAuditRequestItem(auditId, chapters, answers);
    }

    public String getIDSession() {
        return IDSession;
    }

    public void setIDSession(String IDSession) {
        this.IDSession = IDSession;
    }

    public RequestModule getModule() {
        return Module;
    }

    public void setModule(RequestModule module) {
        Module = module;
    }

    public RequestUser getUser() {
        return User;
    }

    public void setUser(RequestUser user) {
        User = user;
    }

    public SaveAuditRequestItem getParameter1() {
        return Parameter1;
    }

    public void setParameter1(SaveAuditRequestItem parameter1) {
        Parameter1 = parameter1;
    }

    public class SaveAuditRequestItem {
        String IDLPAAudit;
        List<SaveAuditRequestQuestion> AuditQuestions = new ArrayList<>();

        public SaveAuditRequestItem() {
        }

        public SaveAuditRequestItem(String auditId, List<Chapter> chapters, List<Answer> answers) {
            this.IDLPAAudit = auditId;
            for (Chapter chapter : chapters) {
                List<Answer> chapterAnswer = new ArrayList<>();
                for (Answer answer : answers) {
                    if (answer.getChapterId().equals(chapter.getChapterId())) {
                        chapterAnswer.add(answer);
                    }
                }
                SaveAuditRequestQuestion saveAuditRequestQuestion = new SaveAuditRequestQuestion(chapter.getChapterId(), chapterAnswer);
                this.AuditQuestions.add(saveAuditRequestQuestion);
            }
        }

        public String getIDLPAAudit() {
            return IDLPAAudit;
        }

        public void setIDLPAAudit(String IDLPAAudit) {
            this.IDLPAAudit = IDLPAAudit;
        }

        public List<SaveAuditRequestQuestion> getAuditQuestions() {
            return AuditQuestions;
        }

        public void setAuditQuestions(List<SaveAuditRequestQuestion> auditQuestions) {
            AuditQuestions = auditQuestions;
        }

        public class SaveAuditRequestQuestion {
            SARQChapter Chapter;
            List<SARQQuestionItem> Questions = new ArrayList<>();

            public SaveAuditRequestQuestion(String chapter, List<Answer> answers) {
                this.Chapter = new SARQChapter(chapter);

                for (Answer answer : answers) {
                    this.Questions.add(new SARQQuestionItem(answer));
                }
            }

            public SARQChapter getChapter() {
                return Chapter;
            }

            public void setChapter(SARQChapter chapter) {
                Chapter = chapter;
            }

            public List<SARQQuestionItem> getQuestions() {
                return Questions;
            }

            public void setQuestions(List<SARQQuestionItem> questions) {
                Questions = questions;
            }

            public class SARQChapter {
                String IDChapter;

                public SARQChapter(String chapterId) {
                    this.IDChapter = chapterId;
                }

                public String getIDChapter() {
                    return IDChapter;
                }

                public void setIDChapter(String IDChapter) {
                    this.IDChapter = IDChapter;
                }
            }

            public class SARQQuestionItem {
                SARQAnswer Answer;
                SARQQuestion Question;

                public SARQQuestionItem(Answer answer) {
                    this.Answer = new SARQAnswer(answer);
                    this.Question = new SARQQuestion(answer.getQuestionId());
                }

                public SARQAnswer getAnswer() {
                    return Answer;
                }

                public void setAnswer(SARQAnswer answer) {
                    Answer = answer;
                }

                public SARQQuestion getQuestion() {
                    return Question;
                }

                public void setQuestion(SARQQuestion question) {
                    Question = question;
                }

                public class SARQAnswer {
                    String Answered;
                    String IDAnsweredBy;
                    String IDDoc;
                    String IDLPAAudit;
                    int ImmediatelyCorrected;
                    String Info1;
                    int NotOk;
                    int Ok;

                    public SARQAnswer(Answer answer) {
                        this.Answered = answer.getAnsweredDate();
                        this.IDAnsweredBy = answer.getAnsweredByUserId();
                        this.IDDoc = answer.getDocId();
                        this.IDLPAAudit = answer.getAuditId();
                        this.ImmediatelyCorrected = answer.getImmediatelyCorrected();
                        this.Info1 = answer.getInfo();
                        this.NotOk = answer.getNotOk();
                        this.Ok = answer.getOk();
                    }

                    public String getAnswered() {
                        return Answered;
                    }

                    public void setAnswered(String answered) {
                        Answered = answered;
                    }

                    public String getIDAnsweredBy() {
                        return IDAnsweredBy;
                    }

                    public void setIDAnsweredBy(String IDAnsweredBy) {
                        this.IDAnsweredBy = IDAnsweredBy;
                    }

                    public String getIDDoc() {
                        return IDDoc;
                    }

                    public void setIDDoc(String IDDoc) {
                        this.IDDoc = IDDoc;
                    }

                    public String getIDLPAAudit() {
                        return IDLPAAudit;
                    }

                    public void setIDLPAAudit(String IDLPAAudit) {
                        this.IDLPAAudit = IDLPAAudit;
                    }

                    public int getImmediatelyCorrected() {
                        return ImmediatelyCorrected;
                    }

                    public void setImmediatelyCorrected(int immediatelyCorrected) {
                        ImmediatelyCorrected = immediatelyCorrected;
                    }

                    public String getInfo1() {
                        return Info1;
                    }

                    public void setInfo1(String info1) {
                        Info1 = info1;
                    }

                    public int getNotOk() {
                        return NotOk;
                    }

                    public void setNotOk(int notOk) {
                        NotOk = notOk;
                    }

                    public int getOk() {
                        return Ok;
                    }

                    public void setOk(int ok) {
                        Ok = ok;
                    }
                }

                public class SARQQuestion {
                    String IDQuestion;

                    public SARQQuestion(String questionId) {
                        this.IDQuestion = questionId;
                    }

                    public String getIDQuestion() {
                        return IDQuestion;
                    }

                    public void setIDQuestion(String IDQuestion) {
                        this.IDQuestion = IDQuestion;
                    }
                }
            }
        }
    }

}
