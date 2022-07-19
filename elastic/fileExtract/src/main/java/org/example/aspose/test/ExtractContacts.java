package org.example.aspose.test;

import com.aspose.email.*;

public class ExtractContacts {
    public ExtractContacts(String filepath) {
        final PersonalStorage pst = PersonalStorage.fromFile(filepath);

        FolderInfoCollection collection = pst.getRootFolder().getSubFolders();

        for(int i=0; i< collection.size(); i++) {
            FolderInfo fi = collection.get_Item(i);

            MessageInfoCollection messageInfoCollection = fi.getContents();

            for(int j=0; j<messageInfoCollection.size(); j++){
                MessageInfo messageInfo = (MessageInfo) messageInfoCollection.get_Item(j);
                //  Get the contact information
                MapiContact contact = (MapiContact) pst.extractMessage(messageInfo).toMapiMessageItem();
                //  Display some contents on screen
                System.out.println(j + 1);
                System.out.println("Name: " + contact.getNameInfo().getDisplayName());
                System.out.println("Phone: " + contact.getTelephones().getMobileTelephoneNumber());
                System.out.println("Phone: " + contact.getPhysicalAddresses().getWorkAddress() + "\n");

                //  Save to disk in MSG format
//                if (contact.getNameInfo().getDisplayName() != null) {
//                    MapiMessage message = pst.extractMessage(messageInfo); // Get rid of illegal characters that cannot be used as a file name
//                    String messageName = message.getSubject().replace(":", " ").replace("\\", " ").replace("?", " ").replace("/", " ");
//                    message.save(dataDir + messageName + ".msg");
//                }

            }
        }
    }
}
