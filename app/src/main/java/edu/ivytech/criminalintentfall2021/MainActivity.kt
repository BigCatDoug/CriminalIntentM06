package edu.ivytech.criminalintentfall2021

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import edu.ivytech.criminalintentfall2021.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener { view ->
            val crimeDetailViewModel by lazy {
                ViewModelProvider(this@MainActivity).get(CrimeDetailViewModel::class.java)
            }
            var crime = Crime()
            crimeDetailViewModel.addCrime(crime)
            onCrimeSelected(crime.id)
        }
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(currentFragment == null) {
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onCrimeSelected(crimeId: UUID) {
        val fragment = CrimeDetailFragment.newInstance(crimeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}