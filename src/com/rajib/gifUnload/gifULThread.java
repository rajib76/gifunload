package com.rajib.gifUnload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
 
public class gifULThread extends Thread {
 
    private Display display;
    private ProgressBar progressBar;
    private Button btnDownload;
    private Button btnCancel;
    private String txtLocation;
    private String txtTarget;
    private int noOfPages;
    private boolean cancel;
    private int startPageNumber;
 
    public gifULThread(Display display, ProgressBar progressBar, //
            String txtLocation,String txtTarget,int startPageNumber,int noOfpages, Button btnDownload, Button btnCancel) {
        this.display = display;
        this.progressBar = progressBar;
        this.btnDownload = btnDownload;
        this.txtLocation=txtLocation;
        this.txtTarget=txtTarget;
        this.noOfPages=noOfpages;
        this.btnCancel = btnCancel;
        this.startPageNumber=startPageNumber;
        
        
    }
 
    @Override
    public void run() {
        if (display.isDisposed()) {
            return;
        }
        this.updateGUIWhenStart();
        //int count=noOfPages;
		for(int i=startPageNumber;i<=noOfPages;i++)
		{
			   if (cancel) {
	                break;
	            }

			
			String j = String.format("%04d", i);
			String imageUrl = txtLocation+j+".gif";
			String destinationFile = txtTarget+"\\page"+j+".gif";
			//System.out.println("Rajib" + imageUrl);
			//System.out.println("Rajib" + destinationFile);
			try {
				saveImage(imageUrl, destinationFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			this.updateGUIInProgress(imageUrl, i, noOfPages);
		}
        
        this.updateGUIWhenFinish();
    }
 
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		try {
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
			
		} catch (Exception e) {
			System.out.println("not valid");
			//stop=true;
			
			// TODO: handle exception
		}

	}
 
    private void updateGUIWhenStart() {
        display.asyncExec(new Runnable() {
 
            @Override
            public void run() {
            	btnDownload.setEnabled(false);
                btnCancel.setEnabled(true);
            }
        });
    }
 
    private void updateGUIWhenFinish() {
        display.asyncExec(new Runnable() {
 
            @Override
            public void run() {
            	btnDownload.setEnabled(true);
            	btnCancel.setEnabled(false);
                progressBar.setSelection(0);
                progressBar.setMaximum(1);
/*                if (cancel) {
                    labelInfo.setText("Cancelled!");
                } else {
                    labelInfo.setText("Finished!");
                }*/
            }
        });
    }
 
    private void updateGUIInProgress(String imageurl, int value, int noOfPages) {
        display.asyncExec(new Runnable() {
 
            @Override
            public void run() {
                //labelInfo.setText("Copying file: " + file.getAbsolutePath());
            	//count=noOfPages;
                progressBar.setMaximum(noOfPages);
                progressBar.setSelection(value);
            }
        });
    }
 
    public void cancel() {
        this.cancel = true;
    }
 
}
