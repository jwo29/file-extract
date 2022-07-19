package org.example;

import com.pff.*;
import org.apache.james.mime4j.dom.Message;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

public class Test {
    public Test(String filename) {
        try {
            PSTFile pstFile = new PSTFile(filename);
            System.out.println(pstFile.getMessageStore().getDisplayName());
            
            PSTFileContent content = pstFile.getContentHandle();

            System.out.println("Pst file type : " + pstFile.getPSTFileType());
            System.out.println("Pst file encryption type : " + pstFile.getEncryptionType());

            processFolder(pstFile.getRootFolder());

            pstFile.close();
        } catch (Exception err) {
            err.printStackTrace();
        }


    }

    int depth = -1;
    public void processFolder(PSTFolder folder)
            throws PSTException, IOException
    {
        depth++;
        // the root folder doesn't have a display name
        if (depth > 0) {
            printDepth();
            System.out.println(folder.getDisplayName());
        }

        // go through the folders...
        // 재귀적 탐색
        if (folder.hasSubfolders()) {
            Vector<PSTFolder> childFolders = folder.getSubFolders();
            for (PSTFolder childFolder : childFolders) {
                processFolder(childFolder);
            }
        }

        // and now the emails for this folder
        if (folder.getContentCount() > 0) {

            System.out.println("Folder.getContainerClass: " + folder.getContainerClass());
            System.out.println("Folder.getContainerFlags: " + folder.getContainerFlags());

            System.out.println("Content count: " + folder.getContentCount());
            depth++;

            PSTMessage email = (PSTMessage)folder.getNextChild();
            while (email != null) {
                printDepth();

                System.out.println("Body: " + email.getBody());
//                System.out.println("BodyHTML: " + email.getBodyHTML());

                /*
                // 메일 메타데이터 추출
                System.out.println("Email: "+email.getSubject());
                System.out.println("Body: "+email.getBody());

                System.out.println("Sender: "+email.getSenderEmailAddress());
                System.out.println("Sender name: "+email.getSenderName());
                System.out.println("Sender entry id: "+email.getSenderEntryId());
                System.out.println("To: "+email.getDisplayTo());
                System.out.println("CC: "+email.getDisplayCC());
                System.out.println("BCC: "+email.getDisplayBCC());
                System.out.println("Original To: "+email.getOriginalDisplayTo());
                System.out.println("Original CC: "+email.getOriginalDisplayCc());
                System.out.println("Original BCC: "+email.getOriginalDisplayBcc());
                System.out.println("Email address: "+email.getEmailAddress());
                System.out.println("Rcvd representing email addr: "+email.getRcvdRepresentingEmailAddress());
                System.out.println("Sent represenging email addr: "+email.getSentRepresentingEmailAddress());
                System.out.println("Received by address: "+email.getReceivedByAddress());

                // 메일 헤더 추출
                System.out.println("Transport message header: "+email.getTransportMessageHeaders());
                */


                // 첨부파일 추출
                System.out.println();
                System.out.println("Attachments: ");
                System.out.println();

                for(int i=0; i<email.getNumberOfAttachments(); i++) {
                    PSTAttachment attach = email.getAttachment(i);

                    if(attach.getAttachMethod() == 5) { // 5:
                        PSTMessage attachEmail = attach.getEmbeddedPSTMessage();

                        System.out.println(attachEmail.getSubject());
                    }

//                    if (attach.getLongFilename().isEmpty()) {
//                        System.out.println("Attach file name" + attach.getFilename());
//                    } else {
//                        System.out.println("Attach long file name" + attach.getLongFilename());
//                    }

//                    FileInputStream inputStream = (FileInputStream) attach.getFileInputStream();
//                    byte[] bytes = inputStream.readAllBytes();
//
//                    System.out.println(bytes.toString());

                }


                email = (PSTMessage)folder.getNextChild();
            }
            depth--;
        }
        depth--;
    }

    public void printDepth() {
        for (int x = 0; x < depth-1; x++) {
            System.out.print(" | ");
        }
        System.out.print(" |- ");
    }
}
