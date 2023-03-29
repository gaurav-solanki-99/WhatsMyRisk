import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.whatsmyrisk.R

class Step2 :  Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v : View=   inflater.inflate(R.layout.welcome_dynamic_text, container, false)

        v.findViewById<TextView>(R.id.tv1).setText("Step 2")
        v.findViewById<TextView>(R.id.tv2).setText("Register your business")




        return  v
    }
    // Here "layout_login" is a name of layout file
    // created for LoginFragment
}