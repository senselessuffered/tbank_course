package com.example.bestlibrary.librariesclass

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.bestlibrary.R
import com.example.bestlibrary.databinding.ActivityMainBinding
import com.example.bestlibrary.librariesclass.data.LibraryEntity
import com.example.bestlibrary.librariesclass.fragments.LibraryDetailFragment
import com.example.bestlibrary.librariesclass.fragments.LibraryListFragment

class MainActivity : AppCompatActivity(),
    LibraryListFragment.OnLibraryItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private var isLandscape = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.list_container, LibraryListFragment())
            }
        }
    }

    override fun onLibraryItemClick(item: LibraryEntity) {
        val detail = LibraryDetailFragment.newInstance(item)
        if (isLandscape) {
            supportFragmentManager.commit {
                replace(R.id.detail_container, detail)
            }
        } else {
            supportFragmentManager.commit {
                replace(R.id.list_container, detail)
                addToBackStack(null)
            }
        }
    }

    override fun onAddNewItem() {
        val detail = LibraryDetailFragment.newInstance(null)
        if (isLandscape) {
            supportFragmentManager.commit {
                replace(R.id.detail_container, detail)
            }
        } else {
            supportFragmentManager.commit {
                replace(R.id.list_container, detail)
                addToBackStack(null)
            }
        }
    }
}
