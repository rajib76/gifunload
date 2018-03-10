package com.rajib.gifUnload;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class gifDownloadForm {

	protected Shell shell;
	private Text txtLocation;
	private Text txtTarget;
	private Button btnCancel;
	private gifULThread gifULThread = null;
	Display display;
	ProgressBar progressBar;
	private Label lblNoOfPages;
	private Text txtNoOfPages;
	private Button btnBrowse;
	private Button btnDownload;
	private Text txtStartPage;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			gifDownloadForm window = new gifDownloadForm();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		progressBar = new ProgressBar(shell, SWT.HORIZONTAL);
		progressBar.setBounds(133, 200, 245, 21);
		
		lblNoOfPages = new Label(shell, SWT.NONE);
		lblNoOfPages.setBounds(258, 121, 75, 15);
		lblNoOfPages.setText("No of Pages");
		
		txtNoOfPages = new Text(shell, SWT.BORDER);
		txtNoOfPages.setBounds(338, 115, 55, 21);
		
		btnBrowse = new Button(shell, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				DirectoryDialog dlg = new DirectoryDialog(shell);


				String dir = dlg.open();
		        if (dir != null) {
		          // Set the text box to the new selection
		          txtTarget.setText(dir);
		        }
		      }
		    });

		
		btnBrowse.setBounds(338, 77, 55, 21);
		btnBrowse.setText("Browse");
		
		Label lblStartPage = new Label(shell, SWT.NONE);
		lblStartPage.setBounds(53, 121, 55, 15);
		lblStartPage.setText("Start Page");
		
		txtStartPage = new Text(shell, SWT.BORDER);
		txtStartPage.setBounds(133, 118, 55, 21);
		txtStartPage.setText("0");
	    
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */

	
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Download GIF");
		
		Label lblLocation = new Label(shell, SWT.NONE);
		lblLocation.setBounds(53, 47, 55, 15);
		lblLocation.setText("Location");
		
		btnDownload = new Button(shell, SWT.NONE);
		
		txtLocation = new Text(shell, SWT.BORDER);
/*		txtLocation.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(txtLocation.getText().toString().length()>0)
				{
					btnDownload.setEnabled(true);
					
				}
				else
				{
					btnDownload.setEnabled(false);
				}
			}
		});
		System.out.println("length"+txtLocation.getText().toString().length());
*/
		txtLocation.setBounds(133, 41, 260, 21);
		txtLocation.setText("http://apnaorg.com/books/hunter-diary/book/page");
		
		Label lblTarget = new Label(shell, SWT.NONE);
		lblTarget.setBounds(53, 83, 55, 15);
		lblTarget.setText("Target");
		
		txtTarget = new Text(shell, SWT.BORDER);
		txtTarget.setEditable(false);
		txtTarget.setBounds(133, 77, 210, 21);
		
	
		
		
		btnDownload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String imageUrl = txtLocation.getText().toString();
				String destinationFile = txtTarget.getText().toString();
				String noOfPagesstr= txtNoOfPages.getText().toString();
				String startPageNumberstr = txtStartPage.getText().toString();
				if((imageUrl.length()>0)&&(destinationFile.length()>0)&&(noOfPagesstr.length()>0)&&(startPageNumberstr.length()>0))
				{
					
				int noOfpages = Integer.parseInt(txtNoOfPages.getText());
				int startPageNumber =Integer.parseInt(txtStartPage.getText());
				//System.out.println("pages" + noOfpages);	
				gifULThread = new gifULThread(display, progressBar,imageUrl,destinationFile,startPageNumber,noOfpages,btnDownload, btnCancel);
				gifULThread.start();
				}
				else
				{
					MessageDialog.openError(shell, "Error", "Enter required field");

				}
				
/*				String imageUrl = txtLocation.getText().toString();
				String destinationFile = txtTarget.getText().toString();
				//System.out.println("Rajib" + destinationFile);
				try {
					gifUnload.execute(imageUrl,destinationFile);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			}
		});
		btnDownload.setBounds(170, 156, 75, 25);
		btnDownload.setText("Download");
		//btnDownload.setEnabled(false);
		
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
                if (gifULThread != null) {
                	gifULThread.cancel();

			}
			}
		});
		btnCancel.setBounds(272, 156, 75, 25);
		btnCancel.setText("Cancel");
		//btnCancel.setEnabled(false);

	}
}
