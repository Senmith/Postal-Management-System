# QR Code Directory

This directory stores automatically generated QR codes for parcels.

## Purpose

When a new parcel is created in the system, a unique QR code is automatically generated and stored here. The QR code contains the parcel's tracking ID and can be scanned at various checkpoints to update the parcel's status.

## File Naming Convention

QR codes are named using the parcel ID:
```
{parcelId}.png
```

Example: `PCL001.png`, `PCL002.png`, etc.

## Usage

### Automatic Generation

QR codes are automatically generated when:
- A new parcel is added through the web interface
- A parcel is created via the API

### Accessing QR Codes

- **Via Web**: Access through the parcel details page
- **Via API**: The QR code path is included in the parcel response
- **File System**: Located at `src/main/resources/static/qrcodes/`

### Scanning QR Codes

1. Navigate to the "QR Checkpoint" page in the application
2. Use your device's camera to scan the QR code
3. The system will automatically identify the parcel
4. Update the status and location as needed

## Technical Details

### Generation Library
- **ZXing (Zebra Crossing) 3.5.3**
- Format: PNG
- Error Correction: Medium (M)
- Size: 300x300 pixels (default)

### QR Code Content
The QR code contains the parcel ID in plain text format, which can be decoded by any standard QR code reader.

## File Management

### Retention
QR codes are retained indefinitely for historical tracking purposes. Consider implementing a cleanup policy for delivered parcels older than a certain period.

### Backup
QR codes are regenerable from parcel data, but consider including this directory in your backup strategy if you want to maintain original codes.

## .gitignore

This directory structure is tracked in Git, but the actual QR code PNG files are ignored to keep the repository clean. The `.gitignore` file contains:

```
src/main/resources/static/qrcodes/*.png
src/main/resources/static/qrcodes/*.jpg
src/main/resources/static/qrcodes/*.jpeg
```

## Troubleshooting

### QR Code Not Generating

**Symptoms:** New parcels don't have QR codes

**Solutions:**
1. Check application logs for errors
2. Verify the ZXing dependency is properly loaded
3. Ensure write permissions for this directory
4. Check if the QRCodeService is properly initialized

### QR Code Not Accessible

**Symptoms:** 404 error when accessing QR code images

**Solutions:**
1. Verify the file exists in the filesystem
2. Check Spring Boot static resource configuration
3. Ensure the path is correct in the database
4. Restart the application to refresh static resources

### Permission Denied

**Symptoms:** Cannot write QR codes to directory

**Solutions:**
1. Check file system permissions
2. Run application with appropriate user privileges
3. Verify the directory exists and is writable

## Security Considerations

### Access Control
Currently, QR codes are publicly accessible. For production:
- Consider adding authentication
- Implement access logging
- Add rate limiting to prevent abuse

### Data Privacy
QR codes contain parcel IDs. Ensure:
- Parcel IDs don't contain sensitive information
- Access to QR codes is properly controlled
- QR codes for sensitive parcels are handled securely

## Best Practices

1. **Regular Cleanup**: Implement a cleanup policy for old QR codes
2. **Monitoring**: Track QR code generation success rate
3. **Validation**: Verify QR codes after generation
4. **Backup**: Include in backup strategy if needed
5. **Documentation**: Document any custom QR code formats

## Future Enhancements

Planned improvements:
- [ ] Configurable QR code size
- [ ] Custom branding/logos in QR codes
- [ ] Batch QR code generation
- [ ] QR code regeneration feature
- [ ] Analytics on QR code scans
- [ ] Encrypted QR code data
- [ ] Dynamic QR codes with expiration
- [ ] PDF export with QR codes

---

**Note:** Keep this directory clean and organized for optimal application performance.
