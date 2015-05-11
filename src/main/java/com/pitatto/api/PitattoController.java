package com.pitatto.api;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pitatto.request.AreaRequest;
import com.pitatto.request.CategoryRequest;
import com.pitatto.request.CompanyReq;
import com.pitatto.request.CompanyInfoRequest;
import com.pitatto.request.FavoriteListRequest;
import com.pitatto.request.FavoriteRequest;
import com.pitatto.request.InsertPasswordRequest;
import com.pitatto.request.LoginRequest;
import com.pitatto.request.LogoutRequest;
import com.pitatto.request.SearchRequest;
import com.pitatto.request.UserIdAvailabilityRequest;
import com.pitatto.response.AreaResponseEntity;
import com.pitatto.response.BaseResponseEntity;
import com.pitatto.response.Company;
import com.pitatto.response.CompanyInfoResponseEntity;
import com.pitatto.response.FavoriteListResponseEntity;
import com.pitatto.response.IndustrialClassResponseEntity;
import com.pitatto.response.Link;
import com.pitatto.response.LoginResponseEntity;
import com.pitatto.response.SearchResponseEntity;
import com.pitatto.service.PitattoService;
import com.pitatto.utils.ResponseUtil;
import com.pitatto.entity.IndustrialClassLEntity;
import com.pitatto.entity.IndustrialClassMEntity;

@RestController
@RequestMapping("api/v1")
public class PitattoController {
    @Autowired
    PitattoService pitattoService;

    /** HTTPヘッダ(X-AUTH-TOKEN)にセッション情報を載せる */
    /*
    @RequestMapping(value = "login", method = RequestMethod.GET)
    String check(Principal principal) {
        return principal.getName();
    }
    */

    /** 業種リスト取得API */
    @RequestMapping(value = "getCategory", method = RequestMethod.GET)
    ResponseEntity<IndustrialClassResponseEntity> getCompanyIndustrialClassGet(@RequestParam("division") int division) {
        if (division == 0) {
            return new ResponseEntity<IndustrialClassResponseEntity>(pitattoService.getCategoryL(), HttpStatus.OK);
        } else {
            return new ResponseEntity<IndustrialClassResponseEntity>(pitattoService.getCategoryM(division), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "getCategory", method = RequestMethod.POST)
    ResponseEntity<IndustrialClassResponseEntity> getCompanyIndustrialClassPost(@Validated @RequestBody CategoryRequest categoryRequest) {
        if (categoryRequest.getDivision().equals("0")) {
            return new ResponseEntity<IndustrialClassResponseEntity>(pitattoService.getCategoryL(), HttpStatus.OK);
        } else {
            int devision = 0;
            try {
                devision = Integer.parseInt(categoryRequest.getDivision());
            } catch (NumberFormatException e) {
                IndustrialClassResponseEntity ret = new IndustrialClassResponseEntity();
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return new ResponseEntity<IndustrialClassResponseEntity>(ret, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<IndustrialClassResponseEntity>(pitattoService.getCategoryM(devision), HttpStatus.OK);
        }
    }

    /** 地域リスト取得API */
    @RequestMapping(value = "getArea", method = RequestMethod.GET)
    ResponseEntity<AreaResponseEntity> getCountryRegionGet(@RequestParam("area") int area) {
        if (area == 0) {
            return new ResponseEntity<AreaResponseEntity>(pitattoService.getArea(), HttpStatus.OK);
        } else {
            return new ResponseEntity<AreaResponseEntity>(pitattoService.getPrefecture(area), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "getArea", method = RequestMethod.POST)
    ResponseEntity<AreaResponseEntity> getCountryRegionPost(@Validated @RequestBody AreaRequest areaRequest) {
        if (areaRequest.getArea().equals("0")) {
            return new ResponseEntity<AreaResponseEntity>(pitattoService.getArea(), HttpStatus.OK);
        } else {
            int area = 0;
            try {
                area = Integer.parseInt(areaRequest.getArea());
            } catch (NumberFormatException e) {
                AreaResponseEntity ret = new AreaResponseEntity();
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return new ResponseEntity<AreaResponseEntity>(ret, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<AreaResponseEntity>(pitattoService.getPrefecture(area), HttpStatus.OK);
        }
    }

    /** 企業検索結果リスト取得API */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    ResponseEntity<SearchResponseEntity> searchGet(@RequestParam(required = true) String id, @RequestParam(required = false) String category, @RequestParam(required = false) String address, @RequestParam(required = false) String word, @RequestParam(required = false) String offset,
            @RequestParam(required = false) String limit, @RequestParam(required = false) String year, @RequestParam(required = false) String sort) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setId(id);
        searchRequest.setCategory(category);
        searchRequest.setAddress(address);
        searchRequest.setWord(word);
        searchRequest.setOffset(offset);
        searchRequest.setLimit(limit);
        searchRequest.setYear(year);
        searchRequest.setSort(sort);
        return new ResponseEntity<SearchResponseEntity>(pitattoService.search(searchRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    ResponseEntity<SearchResponseEntity> searchPost(@Validated @RequestBody SearchRequest searchRequest) {
        return new ResponseEntity<SearchResponseEntity>(pitattoService.search(searchRequest), HttpStatus.OK);
    }

    /** 企業詳細取得API */
    @RequestMapping(value = "getCompanyInfo", method = RequestMethod.GET)
    ResponseEntity<CompanyInfoResponseEntity> companyInfoGet(@RequestParam String code) {
        CompanyInfoRequest companyInfoRequest = new CompanyInfoRequest();
        companyInfoRequest.setCode(code);
        return new ResponseEntity<CompanyInfoResponseEntity>(pitattoService.getCompanyInfo(companyInfoRequest), HttpStatus.OK);
    }
    @RequestMapping(value = "getCompanyInfo", method = RequestMethod.POST)
    ResponseEntity<CompanyInfoResponseEntity> companyInfoPost(@Validated @RequestBody CompanyInfoRequest companyInfoRequest) {
        return new ResponseEntity<CompanyInfoResponseEntity>(pitattoService.getCompanyInfo(companyInfoRequest), HttpStatus.OK);
    }

    /** 会員IDの使用可否取得API */
    @RequestMapping(value = "userIdAvailability", method = RequestMethod.GET)
    ResponseEntity<BaseResponseEntity> userIdAvailabilityGet(@RequestParam String id) {
        UserIdAvailabilityRequest userIdAvailabilityRequest = new UserIdAvailabilityRequest();
        userIdAvailabilityRequest.setId(id);
        return new ResponseEntity<BaseResponseEntity>(pitattoService.checkUserIdAvailability(userIdAvailabilityRequest), HttpStatus.OK);
    }
    @RequestMapping(value = "userIdAvailability", method = RequestMethod.POST)
    ResponseEntity<BaseResponseEntity> userIdAvailabilityPost(@Validated @RequestBody UserIdAvailabilityRequest userIdAvailabilityRequest) {
        return new ResponseEntity<BaseResponseEntity>(pitattoService.checkUserIdAvailability(userIdAvailabilityRequest), HttpStatus.OK);
    }

    /** Login API */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    ResponseEntity<BaseResponseEntity> login(@RequestParam(required = true) String id, @RequestParam(required = true) String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setId(id);
        loginRequest.setPassword(password);
        return new ResponseEntity<BaseResponseEntity>(pitattoService.login(loginRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    ResponseEntity<LoginResponseEntity> login(@Validated @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<LoginResponseEntity>(pitattoService.login(loginRequest), HttpStatus.OK);
    }

    /** Logout API */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    ResponseEntity<BaseResponseEntity> logout(@RequestParam(required = true) String id) {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setId(id);
        return new ResponseEntity<BaseResponseEntity>(pitattoService.logout(logoutRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    ResponseEntity<BaseResponseEntity> logout(@Validated @RequestBody LogoutRequest logoutRequest) {
        return new ResponseEntity<BaseResponseEntity>(pitattoService.logout(logoutRequest), HttpStatus.OK);
    }

    /** お気に入りリスト取得API */
    @RequestMapping(value = "favoriteList", method = RequestMethod.GET)
    ResponseEntity<FavoriteListResponseEntity> getFavoriteList(@RequestParam(required = true) String id, @RequestParam(required = false) String offset, @RequestParam(required = false) String limit, @RequestParam(required = false) String sort) {
        FavoriteListRequest favoriteListRequest = new FavoriteListRequest();
        favoriteListRequest.setId(id);
        favoriteListRequest.setOffset(offset);
        favoriteListRequest.setLimit(limit);
        favoriteListRequest.setSort(sort);
        return new ResponseEntity<FavoriteListResponseEntity>(pitattoService.getFavoriteList(favoriteListRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "favoriteList", method = RequestMethod.POST)
    ResponseEntity<FavoriteListResponseEntity> getFavoriteList(@Validated @RequestBody FavoriteListRequest favoriteListRequest) {
        return new ResponseEntity<FavoriteListResponseEntity>(pitattoService.getFavoriteList(favoriteListRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "favorite", method = RequestMethod.POST)
    ResponseEntity<BaseResponseEntity> favorite(@Validated @RequestBody FavoriteRequest favoriteRequest) {
        return new ResponseEntity<BaseResponseEntity>(pitattoService.favorite(favoriteRequest), HttpStatus.OK);
    }

    /** 業種リスト設定API（本番では使用しない） */
    @RequestMapping(value = "putCategory", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    IndustrialClassLEntity insertCategoryL(@Validated @RequestBody IndustrialClassLEntity companyIndustrialClassEntity) {
        return pitattoService.save(companyIndustrialClassEntity);
    }

    @RequestMapping(value = "putCategoryM", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    IndustrialClassMEntity insertCategoryM(@Validated @RequestBody IndustrialClassMEntity companyIndustrialClassEntity) {
        return pitattoService.saveM(companyIndustrialClassEntity);
    }

    @RequestMapping(value = "putPassword", method = RequestMethod.POST)
    ResponseEntity<BaseResponseEntity> insertHashedPassword(@Validated @RequestBody InsertPasswordRequest insertPasswordRequest) {
        return new ResponseEntity<BaseResponseEntity>(pitattoService.insertHashedPassword(insertPasswordRequest), HttpStatus.OK);
    }
}
