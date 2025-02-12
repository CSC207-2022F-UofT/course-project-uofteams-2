package make_post.use_case;

public class MakePostResponseModel {
    private boolean creationSuccess;
    private String errorMessage;

    public MakePostResponseModel(boolean creationSuccess, String errorMessage){
        this.creationSuccess = creationSuccess;
        this.errorMessage = errorMessage;
    }
    public boolean isCreationSuccess(){
        return creationSuccess;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
}
