package makePost.drivers;

import com.opencsv.exceptions.CsvException;
import makePost.ui.Main;
import makePost.ui.MakePostView;
import makePost.use_case.MakePostDataAccessInterface;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;

public class MakePostDatabaseAccess implements MakePostDataAccessInterface {

    @Override
    public int getNumberOfPosts(){
        String filePath = "java/Database/numPostsCreated.csv";
        File file = new File(filePath);
        try {
            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            String[] nextRecord;
            int numPostsCreated = Integer.valueOf(csvReader.peek()[0]);
            return numPostsCreated;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setNumberOfPosts(int newNumPostsCreated) {
        int numPostsCreated = this.getNumberOfPosts();
        String filePath = "java/Database/numPostsCreated.csv";
        File file = new File(filePath);

        try {
            FileReader filereader = new FileReader(file);
            // create FileWriter object with file as parameter
            CSVReader csvReader = new CSVReader(filereader);
            // create CSVWriter object filewriter object as parameter
            List<String[]> csvBody = csvReader.readAll();
            csvBody.get(1)[0] = String.valueOf(newNumPostsCreated);
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeAll(csvBody);
            writer.flush();
            // closing writer connection
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePost(Map<String, String> postAttributes) {
        String filePath = "java/Database/posts.csv";
        File file = new File(filePath);
        String[] postAttributes1 = new String[postAttributes.size()];
        postAttributes1[0] = postAttributes.get("postID");
        postAttributes1[1] = postAttributes.get("posterID");
        postAttributes1[2] = postAttributes.get("title");
        postAttributes1[3] = postAttributes.get("mainDescription");
        postAttributes1[4] = postAttributes.get("tags");
        postAttributes1[5] = postAttributes.get("collaborators");
        postAttributes1[6] = postAttributes.get("deadline");
        postAttributes1[7] = postAttributes.get("creationDate");
        postAttributes1[8] = postAttributes.get("favouritedUsersIDs");
        postAttributes1[9] = postAttributes.get("repliesIDs");

        try {
            FileReader filereader = new FileReader(file);
            // create FileWriter object with file as parameter
            CSVReader csvReader = new CSVReader(filereader);
            // create CSVWriter object filewriter object as parameter
            List<String[]> csvBody = csvReader.readAll();
            csvBody.add(postAttributes1);
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeAll(csvBody);
            writer.flush();
            // closing writer connection
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Map<String, String> getCurrentUser() {
        String filePath = "java/Database/currentUser.csv";
        File file = new File(filePath);
        try {
            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
            String[] currentUserArray = csvReader.peek();
            Map<String, String> currentUser = new HashMap<>();
            currentUser.put("currentUserID", currentUserArray[0]);
            currentUser.put("isAdmin", currentUserArray[1]);
            currentUser.put("email", currentUserArray[2]);
            currentUser.put("password", currentUserArray[3]);
            currentUser.put("postIDs", currentUserArray[4]);
            currentUser.put("favouritesIDs", currentUserArray[5]);
            return currentUser;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> getFavouritedUsers() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getReplies() {
        return null;
    }

    @Override
    public int getCurrentPostID() {
        return 0;
    }

    @Override
    public String getCreationDate() {
        return null;
    }

    @Override
    public void setTags(int postID, String tags) {
        List<String> tagsList = new ArrayList<>(Arrays.asList(tags.split(" ")));
        String[] presetTags = MakePostView.TAGS;
        String filePath = "java/Database/tags.csv";
        File file = new File(filePath);
        String[] tagsAndIDs = new String[presetTags.length];
        for(int i = 0; i < presetTags.length; i++){
            if(tagsList.contains(presetTags[i])){
                tagsAndIDs[i] = String.valueOf(postID);
            }
            else{
                tagsAndIDs[i] = "";
            }
        }

        try {
            FileReader filereader = new FileReader(file);
            // create FileWriter object with file as parameter
            CSVReader csvReader = new CSVReader(filereader);
            // create CSVWriter object filewriter object as parameter
            List<String[]> csvBody = csvReader.readAll();
            if(csvBody.size() == 0){
                csvBody.add(presetTags);
            }
            csvBody.add(tagsAndIDs);
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeAll(csvBody);
            writer.flush();
            // closing writer connection
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
