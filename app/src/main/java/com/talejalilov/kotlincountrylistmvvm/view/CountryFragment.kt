package talejalilov.kotlincountries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.talejalilov.kotlincountrylistmvvm.databinding.FragmentCountryBinding
import com.talejalilov.kotlincountrylistmvvm.util.downloadFromUrl
import com.talejalilov.kotlincountrylistmvvm.util.placeHolderProgressBar
import com.talejalilov.kotlincountrylistmvvm.viewModel.CountryViewModel
import com.talejalilov.kotlincountrylistmvvm.viewModel.FeedViewModel

class CountryFragment : Fragment() {

    private var countryUuid = 0
    private lateinit var binding:FragmentCountryBinding


    private lateinit var viewModel: CountryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentCountryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)


        observeLiveData()
    }

    private fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer{ country->

            country?.let {

                binding.selectedCountry  = country
                /*
                binding.countryName.text = country.countryName
                binding.countryCapital.text = country.countryCapital
                binding.countryCurrency.text = country.countryCurrency
                binding.countryLanguage.text = country.countryLanguage
                binding.countryRegion.text = country.countryRegion

                context?.let {
                    binding.countryImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))

                } */


            }


        })


    }

}


