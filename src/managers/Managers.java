package managers;

import managers.HistoryManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Managers {
    static TaskManager getDefault(){
        return (new InMemoryTaskManager());
    }
    static HistoryManager getDefaultHistory(){
        return (new InMemoryHistoryManager());
    }



}
