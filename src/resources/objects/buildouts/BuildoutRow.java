package resources.objects.buildouts;

import resources.Location;
import resources.client_info.visitors.CrcStringTableData;
import resources.common.CRC;

public class BuildoutRow {
	
	private static final int	cellCrc		= CRC.getCrc("object/cell/shared_cell.iff");
	
	private final BuildoutArea	buildoutArea;
	private final Location		location	= new Location();
	private long				objectId;
	private long				containerId;
	private int					type;
	private int					sharedTemplateCrc;
	private int					cellIndex;
	private float				radius;
	private int					portalLayoutCrc;
	private String				template;
	
	public BuildoutRow(BuildoutArea buildoutArea) {
		this.buildoutArea = buildoutArea;
	}
	
	public void load(Object [] datatableRow, CrcStringTableData crcString) {
		if (datatableRow.length == 11)
			loadSmall(datatableRow, crcString);
		else if (datatableRow.length == 14)
			loadLarge(datatableRow, crcString);
		else
			throw new IllegalArgumentException("Datatable row must be either 11 or 14 columns!");
		translateLocation();
	}
	
	private void loadSmall(Object [] datatableRow, CrcStringTableData crcString) {
		loadEndColumns(datatableRow, crcString, 0);
		if (portalLayoutCrc != 0) { // is building
			objectId = buildoutArea.getBuildingObjectId();
			buildoutArea.setBuildingObjectId(buildoutArea.getBuildingObjectId() + 1);
			buildoutArea.setCurrentBuilding(objectId);
			containerId = 0;
		} else if (sharedTemplateCrc == cellCrc) {
			objectId = buildoutArea.getBuildingObjectId();
			buildoutArea.setBuildingObjectId(buildoutArea.getBuildingObjectId() + 1);
			buildoutArea.setCurrentCell(objectId);
			containerId = buildoutArea.getCurrentBuilding();
		} else if (cellIndex > 0) { // is in cell
			objectId = buildoutArea.getObjectIdBase();
			buildoutArea.setObjectIdBase(buildoutArea.getObjectIdBase() + 1);
			containerId = buildoutArea.getCurrentCell();
		} else { // is somewhere else
			objectId = buildoutArea.getObjectIdBase();
			buildoutArea.setObjectIdBase(buildoutArea.getObjectIdBase() + 1);
			containerId = 0;
		}
	}
	
	private void loadLarge(Object [] datatableRow, CrcStringTableData crcString) {
		objectId = ((Number) datatableRow[0]).longValue();
		containerId = ((Number) datatableRow[1]).longValue();
		type = (Integer) datatableRow[2];
		loadEndColumns(datatableRow, crcString, 3);
		long indexShifted = ((long) buildoutArea.getIndex() + 1) << 48;
		if (objectId < 0)
			objectId ^= indexShifted;
		if (containerId < 0)
			containerId ^= indexShifted;
	}
	
	private void loadEndColumns(Object [] datatableRow, CrcStringTableData crcString, int offset) {
		sharedTemplateCrc = (Integer) datatableRow[offset + 0];
		cellIndex = (Integer) datatableRow[offset + 1];
		location.setX((Float) datatableRow[offset + 2]);
		location.setY((Float) datatableRow[offset + 3]);
		location.setZ((Float) datatableRow[offset + 4]);
		location.setOrientationW((Float) datatableRow[offset + 5]);
		location.setOrientationX((Float) datatableRow[offset + 6]);
		location.setOrientationY((Float) datatableRow[offset + 7]);
		location.setOrientationZ((Float) datatableRow[offset + 8]);
		radius = (Float) datatableRow[offset + 9];
		portalLayoutCrc = (Integer) datatableRow[offset + 10];
		template = crcString.getTemplateString(sharedTemplateCrc);
	}
	
	private void translateLocation() {
		if (cellIndex != 0)
			return;
		location.translatePosition(buildoutArea.getX1(), 0, buildoutArea.getZ1());
	}
	
	public Location getLocation() {
		return location;
	}
	
	public long getObjectId() {
		return objectId;
	}
	
	public long getContainerId() {
		return containerId;
	}
	
	public int getType() {
		return type;
	}
	
	public int getSharedTemplateCrc() {
		return sharedTemplateCrc;
	}
	
	public int getCellIndex() {
		return cellIndex;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public int getPortalLayoutCrc() {
		return portalLayoutCrc;
	}
	
	public String getTemplate() {
		return template;
	}
	
}
