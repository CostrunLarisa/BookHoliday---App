package com.unibuc.ro.service;

import com.unibuc.ro.model.Address;


public interface AddressService extends CrudService<Address> {
    Address findByAccommodationName(String name);}
