package com.faskn.coinstalker.fragments


import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.faskn.coinstalker.R
import com.faskn.coinstalker.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_connection.*


class ConnectionFragment : BaseFragment() {

    private val bottomNav by lazy { activity?.bottom_navigation }
    private val toolbar by lazy { activity?.toolbar }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationFadein =
            AnimationUtils.loadAnimation(this.context, com.faskn.coinstalker.R.anim.fade_in)
        val animationFadeout =
            AnimationUtils.loadAnimation(this.context, com.faskn.coinstalker.R.anim.fade_out)


        viewModel.connectionStatusLiveData.observe(this@ConnectionFragment, Observer { status ->
            if (status) {
                navigate(R.id.action_connectionFragment_to_coinsFragment)
                bottomNav?.visibility = View.VISIBLE
                toolbar?.visibility = View.VISIBLE
                Log.v("Connection Info", "Connection successful.")
            } else {
                pb_connection.visibility = View.GONE
                tv_error.startAnimation(animationFadein)
                bottomNav?.visibility = View.GONE
                toolbar?.visibility = View.GONE
                Log.v("Connection Info", "No internet connection.")
            }
        })

        btn_tryagain.setOnClickListener {

            btn_tryagain.isEnabled = false
            pb_connection.visibility = View.VISIBLE
            tv_error.startAnimation(animationFadeout)


            object : CountDownTimer(3000, 1000) {
                override fun onFinish() {
                    btn_tryagain.isEnabled = true
                    viewModel.checkConnectionStatus()
                }

                override fun onTick(millisUntilFinished: Long) {
                }
            }.start()
        }
    }


}
