package org.example.aspose.test;

import com.aspose.email.*;
import com.aspose.email.system.IDisposable;

public class ExtractAttachments {
    public ExtractAttachments(String filepath) {
        final PersonalStorage pst = PersonalStorage.fromFile(filepath);

        try {
//            FolderInfo folder = pst.getRootFolder().getSubFolder("Inbox");
            FolderInfoCollection folderInfoCollection = pst.getRootFolder().getSubFolders();

            // Browse through each folder to display folder name and number of messages
            for (int i = 0; i < folderInfoCollection.size(); i++) {
                FolderInfo folder = (FolderInfo) folderInfoCollection.get_Item(i);
//                System.out.println("FolderId: " + folderInfo.getEntryIdString());
//                System.out.println("Folder: " + folderInfo.getDisplayName());
//                System.out.println("Total items: " + folderInfo.getContentCount());
//                System.out.println("Total unread items: " + folderInfo.getContentUnreadCount());
//                System.out.println("-----------------------------------");
                for (String entryId : (Iterable<String>) folder.enumerateMessagesEntryId()) {
                    MapiAttachmentCollection attachments = pst.extractAttachments(entryId);

                    if (attachments.size() != 0) {
                        for (MapiAttachment attachment : (Iterable<MapiAttachment>) attachments) {
                            attachment.save(attachment.getLongFileName());
                        }
                    }
                }

            }


        } finally {
            if (pst != null)
                ((IDisposable) pst).dispose();
        }
    }
}
