package ahm.parts.ordering.helper

import ahm.parts.ordering.BuildConfig
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.TextUtils
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author Prasham Trivedi
 * @modified Ridwan Akbar
 * @version 2.5
 *
 *
 * This class will create a directory having same name as your
 * application. With all the states handled and reported back to
 * developer.
 *
 */
class AppStorageHelper(private val context: Context) {
    /**
     * Get external storage directory
     *
     * @return File object of external storage directory
     */
    val externalStorageDirectory: File = Environment.getExternalStorageDirectory()
    /**
     * get External Cache directory
     *
     * @return File object of External Cache directory
     */
    val externalCacheDirectory: File? = context.externalCacheDir

    private var appDirectory: File? = null
    private var appCacheDirectory: File? = null
    private val canNotWriteFile = "Can not write file: "
    private val canNotCreateDirectory = "Can not create directory: "

    private val availableSpace: Double
        get() {
            val stat = StatFs(Environment.getExternalStorageDirectory().path)
            return stat.availableBlocks.toDouble() * stat.blockSize.toDouble()
        }

    @Throws(ExternalFileWriterException::class)
    fun createFile(fileName: String, inCache: Boolean): File? {
        return createFile(fileName, getAppDirectory(inCache))
    }

    /**
     * Create a file in the app directory with given file name.
     *
     * @param fileName
     * : Desired name of the file
     * @param parent
     * parent of the file
     *
     * @return : File with desired name
     */
    @Throws(ExternalFileWriterException::class)
    private fun createFile(fileName: String, parent: File?): File? {
        if (isExternalStorageAvailable(true)) {
            try {

                if (parent!!.isDirectory) {

                    val detailFile = File(parent, fileName)
                    if (!detailFile.exists())
                        detailFile.createNewFile()
                    else {
                        val messege = "File already there "
                        throwException(messege)
                    }
                    return detailFile
                } else {
                    throwException((parent).toString() + " should be a directory")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                val errorMessege = "IOException $e"
                throwException(errorMessege)
            } catch (e: Exception) {
                e.printStackTrace()
                val errorMessege = "Exception $e"
                throwException(errorMessege)
            }

        }
        return null
    }

    /** Creates app directory  */
    @Throws(ExternalFileWriterException::class)
    private fun createAppDirectory() {
        val directoryName = context.getString(context.applicationInfo.labelRes)

        if (isExternalStorageAvailable(false)) {

            appDirectory = File(
                Environment.getExternalStorageDirectory(),
                "Android/data/" + context.applicationInfo.packageName
            )
            createDirectory(appDirectory!!)

            appCacheDirectory = File(externalCacheDirectory, directoryName)
            createDirectory(appCacheDirectory!!)

        }

    }

    @Throws(ExternalFileWriterException::class)
    private fun isExternalStorageAvailable(isForFile: Boolean): Boolean {
        val errorStarter = if (isForFile) canNotWriteFile else canNotCreateDirectory

        val storageState = Environment.getExternalStorageState()

        if (storageState == Environment.MEDIA_MOUNTED) {
            return true
        } else if (storageState == Environment.MEDIA_BAD_REMOVAL) {
            throwException(errorStarter + "Media was removed before it was unmounted.")
        } else if (storageState == Environment.MEDIA_CHECKING) {
            throwException(
                errorStarter + "Media is present and being disk-checked, "
                        + "Please wait and try after some time"
            )
        } else if (storageState == Environment.MEDIA_MOUNTED_READ_ONLY) {
            throwException(errorStarter + "Presented Media is read only")
        } else if (storageState == Environment.MEDIA_NOFS) {
            throwException(errorStarter + "Blank or unsupported file media")
        } else if (storageState == Environment.MEDIA_SHARED) {
            throwException(errorStarter + "Media is shared with USB mass storage")
        } else if (storageState == Environment.MEDIA_REMOVED) {
            throwException(errorStarter + "Media is not present")
        } else if (storageState == Environment.MEDIA_UNMOUNTABLE) {
            throwException(errorStarter + "Media is present but cannot be mounted")
        } else if (storageState == Environment.MEDIA_UNMOUNTED) {
            throwException(errorStarter + "Media is present but not mounted")
        }

        return false
    }

    @Throws(ExternalFileWriterException::class)
    private fun throwException(errorMessege: String) {
        throw ExternalFileWriterException(errorMessege)
    }

    @Throws(ExternalFileWriterException::class)
    private fun createDirectory(directory: File): File {
        if (!directory.exists() || !directory.isDirectory) {
            if (directory.mkdirs()) {
                val messege = ("directory " + directory + " created : Path "
                        + directory.path)

            } else {
                if (directory.exists()) {
                    if (directory.isDirectory) {
                        val messege = ("directory " + directory + " Already exists : Path "
                                + directory.path)

                    } else {
                        val messege = ((directory).toString()
                                + "should be a directory but found a file : Path "
                                + directory.path)
                        throwException(messege)
                    }

                }
            }
        }
        return directory
    }

    /**
     * Write byte array to file. Will show error if given file is a directory.
     *
     * @param file
     * : File where data is to be written.
     * @param data
     * String which you want to write a file. If size of this is
     * greater than size available, it will show error.
     */
    @Throws(ExternalFileWriterException::class)
    private fun writeDataToFile(file: File?, data: String) {
        val stringBuffer = data.toByteArray()
        writeDataToFile(file, stringBuffer)
    }

    /**
     * Write byte array to file. Will show error if given file is a directory.
     *
     * @param file
     * : File where data is to be written.
     * @param data
     * byte array which you want to write a file. If size of this is
     * greater than size available, it will show error.
     */
    @Throws(ExternalFileWriterException::class)
    private fun writeDataToFile(file: File?, data: ByteArray?) {
        if (isExternalStorageAvailable(true)) {
            if (file!!.isDirectory) {
                throwException((file).toString() + " is not a file, can not write data in it")
            } else {
                if (file != null && data != null) {
                    val dataSize = data.size.toDouble()
                    val remainingSize = availableSpace
                    if (dataSize >= remainingSize) {
                        throwException("Not enough size available")

                    } else {
                        try {
                            val out = FileOutputStream(file)
                            out.write(data)
                            out.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }

            }
        }
    }

    private fun getAppDirectory(inCache: Boolean): File? {
        return if ((inCache)) this.appCacheDirectory else this.appDirectory
    }

    /**
     * Creates subdirectory in application directory
     *
     * @param directoryName
     * name of subdirectory
     *
     * @return File object of created subdirectory
     *
     * @throws ExternalFileWriterException
     * if external storage is not available
     */
    @Throws(ExternalFileWriterException::class)
    fun createSubDirectory(directoryName: String, inCache: Boolean): File? {
        if (isExternalStorageAvailable(false)) {

            getAppDirectory()

            val subDirectory = File(getAppDirectory(inCache), directoryName)

            return createDirectory(subDirectory)
        } else
            return null
    }

    /**
     * Checks whether directory with given name exists in AppDirectory
     *
     * @param directoryName
     * : Name of the directory to check.
     *
     * @return true if a directory with "directoryName" exists, false otherwise
     */
    fun isDirectoryExists(directoryName: String, checkInCache: Boolean): Boolean {
        val parentDirectory = if ((checkInCache)) appCacheDirectory else appDirectory
        return isDirectoryExists(directoryName, parentDirectory)
    }

    /**
     * Check whether file with given name exists in parentDirectory or not.
     *
     * @param fileName
     * : Name of the file to check.
     * @param parentDirectory
     * : Parent directory where directory with "fileName" should be present
     *
     * @return true if a file  with "fileName" exists, false otherwise
     */
    fun isFileExists(fileName: String, parentDirectory: File?): Boolean {
        val directoryToCheck = File(parentDirectory, fileName)
        return directoryToCheck.exists() && directoryToCheck.isFile
    }

    /**
     * Checks whether file with given name exists in AppDirectory
     *
     * @param fileName
     * : Name of the file to check.
     *
     * @return true if a file with "directoryName" exists, false otherwise
     */
    fun isFileExists(fileName: String, checkInCache: Boolean): Boolean {
        val parentDirectory = if ((checkInCache)) appCacheDirectory else appDirectory
        return isFileExists(fileName, parentDirectory)
    }

    /**
     * Check whether directory with given name exists in parentDirectory or not.
     *
     * @param directoryName
     * : Name of the directory to check.
     * @param parentDirectory
     * : Parent directory where directory with "directoryName" should be present
     *
     * @return true if a directory with "directoryName" exists, false otherwise
     */
    fun isDirectoryExists(directoryName: String, parentDirectory: File?): Boolean {
        val directoryToCheck = File(parentDirectory, directoryName)
        return directoryToCheck.exists() && directoryToCheck.isDirectory
    }

    /**
     * Creates subdirectory in parent directory
     *
     * @param parent
     * : Parent directory where directory with "directoryName" should be created
     * @param directoryName
     * name of subdirectory
     *
     * @return File object of created subdirectory
     *
     * @throws ExternalFileWriterException
     * if external storage is not available
     */
    @Throws(ExternalFileWriterException::class)
    fun createSubDirectory(parent: File, directoryName: String): File? {
        if (isExternalStorageAvailable(false)) {

            getAppDirectory()

            if (!parent.isDirectory)
                throwException(parent.name + " Must be a directory ")

            val subDirectory = File(parent, directoryName)

            return createDirectory(subDirectory)
        } else
            return null
    }

    /**
     * Deletes given directory with all its subdirectories and its files.
     *
     * @param directory
     * : Directory to delete
     */
    fun deleteDirectory(directory: File?) {
        if (directory != null) {
            if (directory.isDirectory)
                for (child in directory.listFiles()!!) {

                    if (child != null) {
                        if (child.isDirectory)
                            deleteDirectory(child)
                        else
                            child.delete()
                    }
                }

            directory.delete()
        }
        //		return false;
    }

    /**
     * Get created app directory
     *
     * @return File object of created AppDirectory
     */
    @Throws(ExternalFileWriterException::class)
    fun getAppDirectory(): File? {
        if (appDirectory == null) {
            createAppDirectory()
        }
        return appDirectory
    }

    /**
     * Write data in file of a parent directory
     *
     * @param parent
     * parent directory
     * @param fileName
     * desired filename
     * @param data
     * data
     *
     * @throws ExternalFileWriterException
     * if external storage is not available or free space is
     * less than size of the data
     */
    @Throws(ExternalFileWriterException::class)
    fun writeDataToFile(parent: File, fileName: String, data: ByteArray) {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory()

            val file = createFile(fileName, parent)

            writeDataToFile(file, data)
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     *
     * @param fileName
     * name of the file
     * @param data
     * data to write
     *
     * @throws ExternalFileWriterException
     * if external storage is not available or free space is
     * less than size of the data
     */
    @Throws(ExternalFileWriterException::class)
    fun writeDataToFile(fileName: String, data: String, inCache: Boolean) {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory()

            val file = createFile(fileName, inCache)

            writeDataToFile(file, data)
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     *
     * @param fileName
     * name of the file
     * @param data
     * data to write
     *
     * @throws ExternalFileWriterException
     * if external storage is not available or free space is
     * less than size of the data
     */
    @Throws(ExternalFileWriterException::class)
    fun writeDataToFile(fileName: String, data: ByteArray, inCache: Boolean) {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory()

            val file = createFile(fileName, inCache)

            writeDataToFile(file, data)
        }
    }

    /**
     * Write data in file of a parent directory
     *
     * @param parent
     * parent directory
     * @param fileName
     * desired filename
     * @param data
     * data
     *
     * @throws ExternalFileWriterException
     * if external storage is not available or free space is
     * less than size of the data
     */
    @Throws(ExternalFileWriterException::class)
    fun writeDataToFile(parent: File, fileName: String, data: String) {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory()

            val file = createFile(fileName, parent)

            writeDataToFile(file, data)
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     *
     *
     * Name of the file will be the timestamp.extension
     *
     *
     * @param extension
     * extension of the file, pass null if you don't want to have
     * extension.
     * @param data
     * data to write
     * @param inCache
     * Pass true if you want to write data in External Cache. false if you want to write data in external directory.
     *
     * @throws ExternalFileWriterException
     * if external storage is not available or free space is
     * less than size of the data
     */
    @Throws(ExternalFileWriterException::class)
    fun writeDataToTimeStampedFile(extension: String, data: String, inCache: Boolean) {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory()

            val fileExtension = if ((TextUtils.isEmpty(extension))) "" else ".$extension"
            val fileName = (System.currentTimeMillis()).toString() + fileExtension

            val file = createFile(fileName, getAppDirectory(inCache))

            writeDataToFile(file, data)
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     *
     *
     * Name of the file will be the timestamp.extension
     *
     *
     * @param parent
     * parent directory path
     * @param extension
     * extension of the file, pass null if you don't want to have
     * extension.
     * @param data
     * data to write
     *
     * @throws ExternalFileWriterException
     * if external storage is not available or free space is
     * less than size of the data
     */
    @Throws(ExternalFileWriterException::class)
    fun writeDataToTimeStampedFile(parent: File, extension: String, data: String) {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory()

            val fileExtension = if ((TextUtils.isEmpty(extension))) "" else ".$extension"
            val fileName = (System.currentTimeMillis()).toString() + fileExtension

            val file = createFile(fileName, parent)

            writeDataToFile(file, data)
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     *
     *
     * Name of the file will be the timestamp.extension
     *
     *
     * @param extension
     * extension of the file, pass null if you don't want to have
     * extension.
     * @param data
     * data to write
     *
     * @throws ExternalFileWriterException
     * if external storage is not available or free space is
     * less than size of the data
     */
    @Throws(ExternalFileWriterException::class)
    fun writeDataToTimeStampedFile(extension: String, data: ByteArray, inCache: Boolean) {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory()

            val fileExtension = if ((TextUtils.isEmpty(extension))) "" else ".$extension"
            val fileName = (System.currentTimeMillis()).toString() + fileExtension

            val file = createFile(fileName, getAppDirectory(inCache))

            writeDataToFile(file, data)
        }
    }

    /**
     * Writes data to the file. The file will be created in the directory name
     * same as app.
     *
     *
     * Name of the file will be the timestamp.extension
     *
     *
     * @param parent
     * parent directory path
     * @param extension
     * extension of the file, pass null if you don't want to have
     * extension.
     * @param data
     * data to write
     *
     * @throws ExternalFileWriterException
     * if external storage is not available or free space is
     * less than size of the data
     */
    @Throws(ExternalFileWriterException::class)
    fun writeDataToTimeStampedFile(parent: File, extension: String, data: ByteArray) {
        if (isExternalStorageAvailable(true)) {
            getAppDirectory()

            val fileExtension = if ((TextUtils.isEmpty(extension))) "" else ".$extension"
            val fileName = (System.currentTimeMillis()).toString() + fileExtension

            val file = createFile(fileName, parent)

            writeDataToFile(file, data)
        }
    }

    /**
     * Exception to report back developer about media state or storage state if
     * writing is not
     * possible
     */
    inner class ExternalFileWriterException(messege: String) : Exception(messege)


    fun getMimeType(filePath: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(filePath)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    fun openFile(file: File) {
        try {
            val uri: Uri = if (Build.VERSION.SDK_INT >= 24) {
                FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID + ".ui.base.NoViewModelActivity",
                    file
                )
            } else {
                Uri.fromFile(file)
            }
            val intent = Intent(Intent.ACTION_VIEW)

            val ext = getMimeType(file.path)

            intent.setDataAndType(uri, ext)
            val open = Intent.createChooser(intent, "Open File")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(open)
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    fun getFileName(path: String): String {
        return path.substring(path.lastIndexOf("/") + 1)
    }

    fun getFileExist(path: String, parent: File): File {
        return File(parent.path + File.separator + getFileName(path))
    }

    fun getSubDirectory(directoryName: String, inCache: Boolean): File {
        val subDirectory = File(getAppDirectory(inCache), directoryName)
        if (!subDirectory.exists()) subDirectory.mkdirs()
        return subDirectory
    }

    @Throws(IOException::class)
    fun copyFile(src: File, dst: File) {
        val inChannel = FileInputStream(src).channel
        val outChannel = FileOutputStream(dst).channel
        try {
            inChannel!!.transferTo(0, inChannel.size(), outChannel)
        } finally {
            if (inChannel != null)
                inChannel.close()
            outChannel.close()
        }
    }

}