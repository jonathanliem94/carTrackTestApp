package com.jonathanl.cartracktestapp.data.model

import com.google.gson.annotations.SerializedName

data class WebsiteUser(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("address")
    var addressData: AddressData? = null,
    @SerializedName("phone")
    var phoneNumber: String? = null,
    @SerializedName("website")
    var website: String? = null,
    @SerializedName("company")
    var companyData: CompanyData? = null
)

data class LocationData(
    @SerializedName("lat")
    var lat: String? = null,
    @SerializedName("lng")
    var lng: String? = null
)

data class CompanyData(
    @SerializedName("name")
    var companyName: String? = null,
    @SerializedName("catchPhrase")
    var companyCatchPhrase: String? = null,
    @SerializedName("bs")
    var companyBusiness: String? = null
)

data class AddressData(
    @SerializedName("street")
    var street: String? = null,
    @SerializedName("suite")
    var suite: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("zipcode")
    var zipCode: String? = null,
    @SerializedName("geo")
    var locationData: LocationData? = null
)