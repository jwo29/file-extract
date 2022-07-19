package org.example.aspose.test;

import com.aspose.email.FolderInfo;
import com.aspose.email.FolderInfoCollection;
import com.aspose.email.MessageInfo;
import com.aspose.email.PersonalStorage;
import com.aspose.email.system.IDisposable;

public class LoadPst {
    public LoadPst(String filepath) {


        /*
        // Load a PST file
        PersonalStorage pst = PersonalStorage.fromFile(filepath);

        // Displaying Folders Information
        // Get the folders information
        FolderInfoCollection folderInfoCollection = pst.getRootFolder().getSubFolders();
        // Browse through each folder to display folder name and number of messages
        for (int i = 0; i < folderInfoCollection.size(); i++) {
            FolderInfo folderInfo = (FolderInfo) folderInfoCollection.get_Item(i);
            System.out.println("FolderId: " + folderInfo.getEntryIdString());
            System.out.println("Folder: " + folderInfo.getDisplayName());
            System.out.println("Total items: " + folderInfo.getContentCount());
            System.out.println("Total unread items: " + folderInfo.getContentUnreadCount());
            System.out.println("-----------------------------------");
        }

         */
    }
}
