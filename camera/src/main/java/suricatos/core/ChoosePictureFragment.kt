package suricatos.core

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.sofie.widget.camera.R
import kotlinx.android.synthetic.main.fragment_choose.*

class ChoosePictureFragment: DialogFragment() {

    private lateinit var onTakePictureListener: () -> Unit
    private lateinit var onTakeGalleryListener: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_choose, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).also {
            it.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_take_picture.setOnClickListener {
            if (::onTakePictureListener.isInitialized) onTakePictureListener.invoke()
        }

        button_take_gallery.setOnClickListener {
            if (::onTakeGalleryListener.isInitialized) onTakeGalleryListener.invoke()
        }
    }

    override fun onResume() {
        super.onResume()

        dialog?.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                activity?.onBackPressed()
                true
            } else false
        }
    }

    fun setOnTakePictureListener(listener: () -> Unit) { onTakePictureListener = listener }
    fun setOnTakeGalleryListener(listener: () -> Unit) { onTakeGalleryListener = listener }
}