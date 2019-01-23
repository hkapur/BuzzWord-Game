package controller;

import java.io.IOException;

/**
 * @author Ritwik Banerjee
 */
public interface FileController {

    void handleNewRequest();

    void handleSaveRequest() throws IOException;

    void handleLogin() throws IOException;

    void handleLogout();

    void handleCreateProfile();

    void handleLevelSelect();

    void handleSelectMode();

    void handleHintRequest();

    void handleHelpRequest();

    void handleHomeRequest();

    void handleExitRequest();

    void handleExitRequest2();

    void lost();

    void handleEnd();

    void handlePauseRequest();

    void handlescorereq();

    void handleResumeRequest();

    void req();
}
