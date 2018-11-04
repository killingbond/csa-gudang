package com.ditzweb.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Messagebox;

import com.ditzweb.beans.Items;
import com.ditzweb.beans.Status;

public class ItemsModal {

	private int id = 0;
	private String nama = "";
	private String deskripsi = "";
	private Status status = Status.valueOf("Aktif");
	private Long file;
	private Items items;
	private AImage myImage = null;
	Object contextPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/WEB-INF/classes/WEB-INF/uploads/");

	@Init
	public void showData(@ExecutionArgParam("data") Items obj) throws IOException {
		this.id = obj.getId();
		this.nama = obj.getNama();
		this.deskripsi = obj.getDeskripsi();
		this.file = obj.getFile();
		if (obj.getStatus() != null) {
			this.status = obj.getStatus();
		}
		if (this.file == null|| this.file == 0) {
			myImage = new AImage(contextPath + "\\noimage.jpg");
		}else{
			try {
				myImage = new AImage(contextPath + "\\"+this.file+".jpg");
			} catch (Exception e) {
				myImage = new AImage(contextPath + "\\noimage.jpg");
			}
			
		}

	}

	@Command
	public void add() {
		items = new Items(this.id, this.nama, this.deskripsi, this.status, this.file);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", items);
		BindUtils.postGlobalCommand(null, null, "addItems", dt);
	}
	
	@Command
	public void update() {
		items = new Items(this.id, this.nama, this.deskripsi, this.status, this.file);
		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("obj", items);
		BindUtils.postGlobalCommand(null, null, "updateItems", dt);
	}

	@Command("upload")
	@NotifyChange("myImage")
	public void onImageUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws IOException {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		long millisStart = Calendar.getInstance().getTimeInMillis();

		String filename = contextPath.toString() + "/" + millisStart + ".jpg";
		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			Media media = upEvent.getMedia();
			int lengthofImage = media.getByteData().length;
			if (media instanceof Image) {
				if (lengthofImage > 500 * 1024) {
					showInfo("Please Select a Image of size less than 500Kb.");
					return;
				} else {
					InputStream fin = media.getStreamData();
					in = new BufferedInputStream(fin);
					File baseDir = new File(contextPath.toString());
					if (!baseDir.exists()) {
						baseDir.mkdirs();
					}
					File file = new File(filename);
					OutputStream fout = new FileOutputStream(file);
					out = new BufferedOutputStream(fout);
					byte buffer[] = new byte[1024];
					int ch = in.read(buffer);
					while (ch != -1) {
						out.write(buffer, 0, ch);
						ch = in.read(buffer);
					}
					this.file = millisStart;
					myImage = (AImage) media;// Initialize the bind object to show image in zul page and Notify it also

				}
			} else {
				showInfo("The selected File is not an image.");
			}
		}

	}

	protected void showInfo(String message) {
		Messagebox.show(message, "Alert !!", Messagebox.OK, Messagebox.INFORMATION);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public Long getFile() {
		return file;
	}

	public void setFile(Long file) {
		this.file = file;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public AImage getMyImage() {
		return myImage;
	}

	public void setMyImage(AImage myImage) {
		this.myImage = myImage;
	}

}
