package com.pitatto.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pitatto.entity.CompanyEntity;
import com.pitatto.entity.CompanyIndustrialClassEntity;
import com.pitatto.entity.CountryRegionEntity;
import com.pitatto.entity.FavoriteEntity;
import com.pitatto.entity.IndustrialClassLEntity;
import com.pitatto.entity.IndustrialClassMEntity;
import com.pitatto.entity.PrefectureEntity;
import com.pitatto.entity.RecruitSiteEntity;
import com.pitatto.entity.UserEntity;
import com.pitatto.repository.CompanyAddressRepository;
import com.pitatto.repository.CompanyIndustrialClassRepository;
import com.pitatto.repository.CompanyRepository;
import com.pitatto.repository.CountryRegionRepository;
import com.pitatto.repository.FavoriteRepository;
import com.pitatto.repository.IndustrialClassLRepository;
import com.pitatto.repository.IndustrialClassMRepository;
import com.pitatto.repository.PrefectureRepository;
import com.pitatto.repository.RecruitSiteRepository;
import com.pitatto.repository.UserRepository;
import com.pitatto.request.CompanyInfoRequest;
import com.pitatto.request.CompanyReq;
import com.pitatto.request.FavoriteListRequest;
import com.pitatto.request.FavoriteRequest;
import com.pitatto.request.InsertPasswordRequest;
import com.pitatto.request.LoginRequest;
import com.pitatto.request.LogoutRequest;
import com.pitatto.request.SearchRequest;
import com.pitatto.request.UserIdAvailabilityRequest;
import com.pitatto.response.AreaResponseEntity;
import com.pitatto.response.BaseResponseEntity;
import com.pitatto.response.Category;
import com.pitatto.response.Company;
import com.pitatto.response.CompanyInfoResponseEntity;
import com.pitatto.response.Favorite;
import com.pitatto.response.FavoriteListResponseEntity;
import com.pitatto.response.IdName;
import com.pitatto.response.IndustrialClassResponseEntity;
import com.pitatto.response.Link;
import com.pitatto.response.LoginResponseEntity;
import com.pitatto.response.SearchResponseEntity;
import com.pitatto.response.Site;
import com.pitatto.utils.DateUtils;
import com.pitatto.utils.ResponseUtil;
import com.pitatto.utils.TextUtils;

@Service
@Transactional
public class PitattoService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    IndustrialClassLRepository industrialClassLRepository;

    @Autowired
    IndustrialClassMRepository industrialClassMRepository;

    @Autowired
    CountryRegionRepository countryRegionRepository;

    @Autowired
    PrefectureRepository prefectureRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyIndustrialClassRepository companyIndustrialClassRepository;

    @Autowired
    CompanyAddressRepository companyAddressRepository;

    @Autowired
    RecruitSiteRepository recruitSiteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    /** 業種（大分類）リスト取得 */
    public IndustrialClassResponseEntity getCategoryL() {
        IndustrialClassResponseEntity ret = new IndustrialClassResponseEntity();
        ret.setDivisions(new ArrayList<IdName>());
        List<IndustrialClassLEntity> list = industrialClassLRepository.getDivision();
        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        for (IndustrialClassLEntity l : list) {
            IdName div = new IdName();
            div.setId(String.valueOf(l.getRowid()));
            div.setName(l.getIndustrial_class_l_name());
            ret.getDivisions().add(div);
        }
        return ret;
    }

    /** 業種（中分類）リスト取得 */
    public IndustrialClassResponseEntity getCategoryM(int division) {
        IndustrialClassResponseEntity ret = new IndustrialClassResponseEntity();
        ret.setDivisions(new ArrayList<IdName>());
        List<IndustrialClassMEntity> list = industrialClassMRepository.getDivision(division);
        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        for (IndustrialClassMEntity m : list) {
            IdName div = new IdName();
            div.setId(String.valueOf(m.getRowid()));
            div.setName(m.getIndustrial_class_m_name());
            ret.getDivisions().add(div);
        }
        return ret;
    }

    /** 地域リスト取得 */
    public AreaResponseEntity getArea() {
        AreaResponseEntity ret = new AreaResponseEntity();
        ret.setAreas(new ArrayList<IdName>());
        List<CountryRegionEntity> list = countryRegionRepository.getArea();
        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        for (CountryRegionEntity l : list) {
            IdName div = new IdName();
            div.setId(String.valueOf(l.getRowid()));
            div.setName(l.getCountry_region_formal_name());
            ret.getAreas().add(div);
        }
        return ret;
    }

    /** 都道府県リスト取得 */
    public AreaResponseEntity getPrefecture(int area) {
        AreaResponseEntity ret = new AreaResponseEntity();
        ret.setAreas(new ArrayList<IdName>());
        List<PrefectureEntity> list = prefectureRepository.getArea(area);
        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        for (PrefectureEntity l : list) {
            IdName div = new IdName();
            div.setId(String.valueOf(l.getRowid()));
            div.setName(l.getPrefecture_name());
            ret.getAreas().add(div);
        }
        return ret;
    }

    /** 企業検索結果リスト取得 */
    public SearchResponseEntity search(SearchRequest searchRequest) {
        // 返却オブジェクト
        SearchResponseEntity ret = new SearchResponseEntity();
        ret.setCompanies(new ArrayList<Company>());

        // クエリー
        String queryId = "";
        String queryCategory = "";
        String queryAddress = "";
        String queryWord = "";
        String queryLimit = "";
        String queryYear = "";
        String querySort = "";

        // リクエストパラメータを取得
        String reqId = searchRequest.getId();
        String reqCategory = searchRequest.getCategory();
        String reqAddress = searchRequest.getAddress();
        String reqWord = searchRequest.getWord();
        String reqOffset = searchRequest.getOffset();
        String reqLimit = searchRequest.getLimit();
        String reqYear = searchRequest.getYear();
        String reqSort = searchRequest.getSort();

        int companyId = 0;

        // member id
        if (!TextUtils.isEmpty(reqId)) {
            // 該当レコードを取得
            UserEntity update = userRepository.getByMemberId(Integer.parseInt(reqId));
            if (update == null) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            } else {
                if (!DateUtils.isInSession(update.getUser_password_salt())) {
                    ret.setResultCode(ResponseUtil.RETURN_CODE_3000);
                    ret.setErrMsg(ResponseUtil.RETURN_MSG_3000);
                    return ret;
                } else {
                    DateUtils.resetSessionTime(reqId);
                }
            }
            queryId = " AND fav.member_id = " + reqId + " ";
        } else {
            ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
            return ret;
        }

        // 業種
        if (!TextUtils.isEmpty(reqCategory)) {
            reqCategory = reqCategory.replace("\"", "");
            String[] categoryPair = reqCategory.split(",");
            for (String c : categoryPair) {
                String[] cate = c.trim().split("\\+");
                int categoryM = 0;
                int categoryL = 0;
                try {
                    categoryL = Integer.parseInt(cate[0]);
                    categoryM = Integer.parseInt(cate[1]);
                } catch (NumberFormatException e) {
                    ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                    ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                    return ret;
                }
                String onePair = " (ca.industrial_class_l_id = " + categoryL + " " + (categoryM != 0 ? (" AND ca.industrial_class_m_id = " + categoryM + ") ") : ") ");

                queryCategory += (queryCategory.isEmpty() ? " AND (" : " OR ") + onePair;
            }
            queryCategory += queryCategory.isEmpty() ? " " : ") ";
        }

        // 本社住所
        if (!TextUtils.isEmpty(reqAddress)) {
            reqAddress = reqAddress.replace("\"", "");
            String[] addressPair = reqAddress.split(",");
            for (String a : addressPair) {
                String[] address = a.trim().split("\\+");
                int region = 0;
                int prefecture = 0;
                try {
                    region = Integer.parseInt(address[0]);
                    prefecture = Integer.parseInt(address[1]);
                } catch (NumberFormatException e) {
                    ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                    ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                    return ret;
                }
                String onePair = " (ad.country_region_id = " + region + " " + (prefecture != 0 ? (" AND ad.prefecture_id = " + prefecture + ") ") : ") ");
                
                queryAddress += (queryAddress.isEmpty() ? " AND (" : " OR ") + onePair;
            }
            queryAddress += queryAddress.isEmpty() ? " " : ") ";
        }

        // 社名
        if (!TextUtils.isEmpty(reqWord)) {
            queryWord = " AND co.company_formal_name like '%" + reqWord.trim() + "%' ";
        }

        // Limitとオフセット
        int offset = 0;
        if (!TextUtils.isEmpty(reqOffset)) {
            try {
                offset = Integer.parseInt(reqOffset);
            } catch (NumberFormatException e) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }
        }

        // Limit
        int limit = 0;
        if (!TextUtils.isEmpty(reqLimit)) {
            try {
                limit = Integer.parseInt(reqLimit);
            } catch (NumberFormatException e) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }
        }

        if (offset != 0) {
            if (limit != 0) {
                queryLimit = " LIMIT " + limit + " OFFSET " + offset + " ";
            } else {
                queryLimit = " LIMIT 100000 OFFSET " + offset + " ";
            }
        } else {
            if (limit != 0) {
                queryLimit = " LIMIT " + limit + " ";
            } else {
                queryLimit = " ";
            }
        }

        // 年度
        if (!TextUtils.isEmpty(reqYear)) {
            // 今は2016年度のみに固定
        }

        // ソート
        int sort = 0;
        if (!TextUtils.isEmpty(reqSort)) {
            try {
                sort = Integer.parseInt(reqSort);
            } catch (NumberFormatException e) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }
            switch (sort) {
            case 0:
                querySort = "";
                break;
            case 1: // お気に入りランキング
                querySort = " ORDER BY fav.rank ";
                break;
            case 2: // 企業名カナ
                querySort = " ORDER BY co.company_kana_name ";
                break;
            case 3: // 業種カナ
                querySort = " ORDER BY ind.industrial_class_kana_name ";
                break;
            case 4: // 本社所在地カナ
                querySort = " ORDER BY pre.prefecture_kana_name ";
                break;
            default:
                break;
            }
        }

        String queryTotal = "SELECT  COUNT(*) FROM m_company AS co INNER JOIN m_company_industrial_class AS ca JOIN m_company_address AS ad JOIN m_prefecture AS pre JOIN m_industrial_class AS ind ";
        queryTotal += "ON co.rowid = ca.company_id AND co.rowid = ad.company_id AND pre.rowid = ad.prefecture_id AND ind.rowid = ca.industrial_class_id ";
        queryTotal += queryCategory;
        queryTotal += queryAddress;
        queryTotal += queryWord;
        queryTotal += " LEFT OUTER JOIN t_member_favorite AS fav ON co.company_code = fav.company_id ";
        queryTotal += queryId;
        queryTotal += querySort;

        String query = "SELECT  co.company_code, co.company_formal_name, co.company_kana_name, ca.company_industrial_class_note, ind.industrial_class_kana_name, ad.address_1, pre.prefecture_kana_name, co.company_hp_url, co.update_date, co.create_date, co.rowid, ifnull(fav.rank,'0') ";
        query += " FROM m_company AS co INNER JOIN m_company_industrial_class AS ca JOIN m_company_address AS ad JOIN m_prefecture AS pre JOIN m_industrial_class AS ind ";
        query += " ON co.rowid = ca.company_id AND co.rowid = ad.company_id AND pre.rowid = ad.prefecture_id AND ind.rowid = ca.industrial_class_id ";
        query += queryCategory;
        query += queryAddress;
        query += queryWord;
        query += " LEFT OUTER JOIN t_member_favorite AS fav ON co.company_code = fav.company_id ";
        query += queryId;
        query += querySort;
        query += queryLimit;

        Object listTotal = manager.createNativeQuery(queryTotal).getSingleResult();
        ret.setTotal(String.valueOf(((Integer) listTotal).intValue()));

        @SuppressWarnings("unchecked")
        List<Object[]> list = manager.createNativeQuery(query).getResultList();
        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        for (Object[] datas : list) {
            Company com = new Company();
            com.setCode((String) datas[0]);
            com.setName((String) datas[1]);
            com.setKana((String) datas[2]);
            com.setCategory((String) datas[3]);
            com.setArea((String) datas[5]);
            com.setUrl((String) datas[7]);
            com.setLastUpdateDate((String) datas[8]);
            com.setRegistDate((String) datas[9]);
            companyId = ((Integer) datas[10]).intValue();
            com.setRank((String) datas[11]);
            // all site list
            com.setLinks(new ArrayList<Site>());
            String queryAllSiteList = "SELECT site.recruit_site_name FROM m_recruit_site AS site ";
            @SuppressWarnings("unchecked")
            List<String> allSiteList = manager.createNativeQuery(queryAllSiteList).getResultList();
            for (String alll : allSiteList) {
                Site site = new Site();
                site.setAvailable(0);
                site.setSiteName((String) alll);
                com.getLinks().add(site);
            }
            // site list
            String querySiteList = "SELECT recr.exist_flag, site.recruit_site_name FROM t_company_recruit_site_page AS recr INNER JOIN m_recruit_site AS site ";
            querySiteList += "ON site.rowid = recr.recruit_site_year_id AND recr.company_id = " + companyId + " order by recr.sort_order";
            @SuppressWarnings("unchecked")
            List<Object[]> siteList = manager.createNativeQuery(querySiteList).getResultList();
            for (Object[] l : siteList) {
                for (Site site : com.getLinks()) {
                    if (site.getSiteName().equals((String) l[1])) {
                        site.setAvailable(((Integer) l[0]).intValue());
                        break;
                    }
                }
            }
            ret.getCompanies().add(com);
        }
        return ret;
    }

    /** 企業詳細情報取得 */
    public CompanyInfoResponseEntity getCompanyInfo(CompanyInfoRequest companyInfoRequest) {
        // 返却オブジェクト
        CompanyInfoResponseEntity ret = new CompanyInfoResponseEntity();

        // クエリー
        String queryCode = "";

        int companyId = 0;

        // リクエストパラメータを取得
        String reqCode = companyInfoRequest.getCode().replace("\"", "");

        // 業種
        if (!TextUtils.isEmpty(reqCode)) {
            int code = 0;
            try {
                code = Integer.parseInt(reqCode);
            } catch (NumberFormatException e) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }
            queryCode = " AND co.company_code = " + code + " ";
        } else {
            ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
            return ret;
        }

        String query = "SELECT  co.company_formal_name, ad.address_1, ad.address_2, co.company_hp_url, co.update_date, ad.zip, ad.address_all, ad.tel_number, co.company_establishment, cap.capital_amount_text, rep.representative_name, co.rowid ";
        query += "FROM m_company AS co INNER JOIN m_company_industrial_class AS ca JOIN m_company_address AS ad JOIN m_prefecture AS pre JOIN t_company_capital AS cap JOIN t_company_representative AS rep ";
        query += "ON co.rowid = ca.company_id AND co.rowid = ad.company_id AND pre.rowid = ad.prefecture_id AND co.rowid = cap.company_id AND co.rowid = rep.company_id ";
        query += queryCode;

        @SuppressWarnings({ "unchecked" })
        List<Object[]> list = manager.createNativeQuery(query).getResultList();
        Object[] datas = list.get(0);
        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        ret.setName((String) datas[0]);
        ret.setArea((String) datas[1] + (String) datas[2]);
        ret.setUrl((String) datas[3]);
        ret.setUpdateDate((String) datas[4]);
        ret.setZip((String) datas[5]);
        ret.setAddress((String) datas[6]);
        ret.setTel((String) datas[7]);
        ret.setEstablishment((String) datas[8]);
        ret.setCapital((String) datas[9]);
        ret.setRepresentativeName((String) datas[10]);
        companyId = ((Integer) datas[11]).intValue();
        // all site list
        ret.setLinks(new ArrayList<Link>());
        String queryAllSiteList = "SELECT site.recruit_site_name FROM m_recruit_site AS site ";
        @SuppressWarnings("unchecked")
        List<String> allSiteList = manager.createNativeQuery(queryAllSiteList).getResultList();
        for (String alll : allSiteList) {
            Link site = new Link();
            site.setSiteUrl("");
            site.setSiteName((String) alll);
            ret.getLinks().add(site);
        }
        // available site list
        String querySiteList = "SELECT recr.company_recruit_site_page_url, site.recruit_site_name FROM t_company_recruit_site_page AS recr INNER JOIN m_recruit_site AS site ";
        querySiteList += "ON site.rowid = recr.recruit_site_year_id AND recr.company_id = " + companyId + " order by recr.sort_order";
        @SuppressWarnings("unchecked")
        List<Object[]> siteList = manager.createNativeQuery(querySiteList).getResultList();
        for (Object[] l : siteList) {
            for (Link link : ret.getLinks()) {
                if (link.getSiteName().equals((String) l[1])) {
                    link.setSiteUrl((String) l[0]);
                    break;
                }
            }
        }
        // category list
        ret.setCategory(new ArrayList<Category>());
        String queryCategoryList = "SELECT ind.company_industrial_class_note, ind.main_industrial_class_flag FROM m_company_industrial_class AS ind ";
        queryCategoryList += "WHERE ind.company_id = " + companyId + " order by ind.sort_order";
        @SuppressWarnings("unchecked")
        List<Object[]> categoryList = manager.createNativeQuery(queryCategoryList).getResultList();
        for (Object[] l : categoryList) {
            Category category = new Category();
            category.setCategoryName((String) l[0]);
            category.setMainFlag(((Integer) l[1]).intValue());
            ret.getCategory().add(category);
        }
        return ret;
    }

    /** 会員IDの使用可否取得 */
    public BaseResponseEntity checkUserIdAvailability(UserIdAvailabilityRequest userIdAvailabilityRequest) {
        // 返却オブジェクト
        BaseResponseEntity ret = new BaseResponseEntity();

        // リクエストパラメータを取得
        String reqId = userIdAvailabilityRequest.getId();

        // クエリ文字列
        String query = "";

        if (!TextUtils.isEmpty(reqId)) {
            query = "SELECT * FROM m_user WHERE user_login_code = " + reqId;
        } else {
            ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
            return ret;
        }

        @SuppressWarnings("unchecked")
        List<Object[]> list = manager.createNativeQuery(query).getResultList();
        if (list.size() == 0) {
            ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        } else {
            ret.setResultCode(ResponseUtil.RETURN_CODE_1500);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_1500);
        }
        return ret;
    }

    /** login */
    public LoginResponseEntity login(LoginRequest loginRequest) {
        // 返却オブジェクト
        LoginResponseEntity ret = new LoginResponseEntity();

        // リクエストパラメータを取得
        String loginId = loginRequest.getId();
        String password = loginRequest.getPassword();

        if (TextUtils.isEmpty(loginId) || TextUtils.isEmpty(password)) {
            ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
            return ret;
        }

        // 該当レコードを取得
        UserEntity update = userRepository.getByUserLoginCode(loginId);

        if (update == null) {
            ret.setResultCode(ResponseUtil.RETURN_CODE_1000);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_1000);
            return ret;
        }

        if (TextUtils.isEmpty(update.getUser_password_hash())) {
            ret.setResultCode(ResponseUtil.RETURN_CODE_1000);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_1000);
            return ret;
        }

        // Login IDからソルト値を生成（SHA-512）
        String saltedId = hashKey(loginId);

        // パスワードにSalt値を足してからハッシュ値を生成（SHA-512）
        String hashedPassword = hashKey(saltedId + password);

        if (!hashedPassword.equals(update.getUser_password_hash())) {
            ret.setResultCode(ResponseUtil.RETURN_CODE_1000);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_1000);
            return ret;
        }

        update.setUser_password_salt(DateUtils.getNowDate());
        userRepository.save(update);

        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        ret.setMemberId(String.valueOf(update.getMemberId()));
        return ret;
    }

    /** ログアウト */
    public BaseResponseEntity logout(LogoutRequest logoutRequest) {
        // 返却オブジェクト
        BaseResponseEntity ret = new BaseResponseEntity();

        // リクエストパラメータを取得
        String loginId = logoutRequest.getId();

        if (TextUtils.isEmpty(loginId)) {
            ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
            return ret;
        }

        // 該当レコードを取得
        UserEntity update = userRepository.getByMemberId(Integer.parseInt(loginId));

        if (update == null) {
            ret.setResultCode(ResponseUtil.RETURN_CODE_0002);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_0002);
            return ret;
        }

        update.setUser_password_salt(DateUtils.getExpiredDate());
        userRepository.save(update);

        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);

        return ret;
    }

    /** お気に入りリスト取得 */
    public FavoriteListResponseEntity getFavoriteList(FavoriteListRequest favoriteListRequest) {
        // 返却オブジェクト
        FavoriteListResponseEntity ret = new FavoriteListResponseEntity();
        ret.setCompanies(new ArrayList<Favorite>());

        // クエリー
        String queryId = "";
        String queryLimit = "";
        String querySort = "";

        // リクエストパラメータを取得
        String reqId = favoriteListRequest.getId();
        String reqOffset = favoriteListRequest.getOffset();
        String reqLimit = favoriteListRequest.getLimit();
        String reqSort = favoriteListRequest.getSort();

        int companyId = 0;

        // member id
        if (!TextUtils.isEmpty(reqId)) {
            // 該当レコードを取得
            UserEntity update = userRepository.getByMemberId(Integer.parseInt(reqId));
            if (update == null) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            } else {
                if (!DateUtils.isInSession(update.getUser_password_salt())) {
                    ret.setResultCode(ResponseUtil.RETURN_CODE_3000);
                    ret.setErrMsg(ResponseUtil.RETURN_MSG_3000);
                    return ret;
                } else {
                    DateUtils.resetSessionTime(reqId);
                }
            }
            queryId = " AND fav.member_id = " + reqId + " ";
        } else {
            ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
            return ret;
        }

        // Limitとオフセット
        int offset = 0;
        if (!TextUtils.isEmpty(reqOffset)) {
            try {
                offset = Integer.parseInt(reqOffset);
            } catch (NumberFormatException e) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }
        }

        // Limit
        int limit = 0;
        if (!TextUtils.isEmpty(reqLimit)) {
            try {
                limit = Integer.parseInt(reqLimit);
            } catch (NumberFormatException e) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }
        }

        if (offset != 0) {
            if (limit != 0) {
                queryLimit = " LIMIT " + limit + " OFFSET " + offset + " ";
            } else {
                queryLimit = " LIMIT 100000 OFFSET " + offset + " ";
            }
        } else {
            if (limit != 0) {
                queryLimit = " LIMIT " + limit + " ";
            } else {
                queryLimit = " ";
            }
        }

        // ソート
        int sort = 0;
        if (!TextUtils.isEmpty(reqSort)) {
            try {
                sort = Integer.parseInt(reqSort);
            } catch (NumberFormatException e) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }
            switch (sort) {
            case 0:
                querySort = "";
                break;
            case 1: // お気に入りランキング
                querySort = " ORDER BY fav.rank";
                break;
            case 2: // 企業名カナ
                querySort = " ORDER BY co.company_kana_name ";
                break;
            case 3: // 業種カナ
                querySort = " ORDER BY ind.industrial_class_kana_name ";
                break;
            case 4: // 本社所在地カナ
                querySort = " ORDER BY pre.prefecture_kana_name ";
                break;
            default:
                break;
            }
        }

        String queryTotal = "SELECT  COUNT(*) FROM m_company AS co INNER JOIN m_company_industrial_class AS ca JOIN m_company_address AS ad JOIN m_prefecture AS pre JOIN m_industrial_class AS ind JOIN t_member_favorite AS fav ";
        queryTotal += "ON co.rowid = ca.company_id AND co.rowid = ad.company_id AND pre.rowid = ad.prefecture_id AND ind.rowid = ca.industrial_class_id AND fav.company_id = co.company_code ";
        queryTotal += queryId;
        queryTotal += querySort;

        String query = "SELECT  co.company_code, co.company_name, co.company_kana_name, ca.company_industrial_class_note, ind.industrial_class_kana_name, ad.address_1, pre.prefecture_kana_name, co.company_hp_url, fav.rank, co.rowid ";
        query += "FROM m_company AS co INNER JOIN m_company_industrial_class AS ca JOIN m_company_address AS ad JOIN m_prefecture AS pre JOIN m_industrial_class AS ind JOIN t_member_favorite AS fav ";
        query += "ON co.rowid = ca.company_id AND co.rowid = ad.company_id AND pre.rowid = ad.prefecture_id AND ind.rowid = ca.industrial_class_id AND fav.company_id = co.company_code ";
        query += queryId;
        query += querySort;
        query += queryLimit;

        Object listTotal = manager.createNativeQuery(queryTotal).getSingleResult();
        ret.setTotal(String.valueOf(((Integer) listTotal).intValue()));

        @SuppressWarnings("unchecked")
        List<Object[]> list = manager.createNativeQuery(query).getResultList();
        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        for (Object[] datas : list) {
            Favorite com = new Favorite();
            com.setCode((String) datas[0]);
            com.setName((String) datas[1]);
            com.setCategory((String) datas[3]);
            com.setArea((String) datas[5]);
            com.setUrl((String) datas[7]);
            com.setRank((String) datas[8]);
            companyId = ((Integer) datas[9]).intValue();
            // all site list
            com.setLinks(new ArrayList<Site>());
            String queryAllSiteList = "SELECT site.recruit_site_name FROM m_recruit_site AS site ";
            @SuppressWarnings("unchecked")
            List<String> allSiteList = manager.createNativeQuery(queryAllSiteList).getResultList();
            for (String alll : allSiteList) {
                Site site = new Site();
                site.setAvailable(0);
                site.setSiteName((String) alll);
                com.getLinks().add(site);
            }
            // site list
            String querySiteList = "SELECT recr.exist_flag, site.recruit_site_name FROM t_company_recruit_site_page AS recr INNER JOIN m_recruit_site AS site ";
            querySiteList += "ON site.rowid = recr.recruit_site_year_id AND recr.company_id = " + companyId + " order by recr.sort_order";
            @SuppressWarnings("unchecked")
            List<Object[]> siteList = manager.createNativeQuery(querySiteList).getResultList();
            for (Object[] l : siteList) {
                for (Site site : com.getLinks()) {
                    if (site.getSiteName().equals((String) l[1])) {
                        site.setAvailable(((Integer) l[0]).intValue());
                        break;
                    }
                }
            }
            ret.getCompanies().add(com);
        }
        return ret;
    }

    /** お気に入り管理 */
    public BaseResponseEntity favorite(FavoriteRequest favoriteRequest) {

        boolean isNew = false;
        // 返却オブジェクト
        BaseResponseEntity ret = new BaseResponseEntity();

        for (CompanyReq c : favoriteRequest.getCompany()) {

            // リクエストパラメータを取得
            String id = c.getId();
            String code = c.getCode();
            String rank = c.getRank();

            if (TextUtils.isEmpty(id) || TextUtils.isEmpty(code)) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }
            // member id
            if (!TextUtils.isEmpty(id)) {
                // 該当レコードを取得
                UserEntity update = userRepository.getByMemberId(Integer.parseInt(id));
                if (update == null) {
                    ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                    ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                    return ret;
                } else {
                    if (!DateUtils.isInSession(update.getUser_password_salt())) {
                        ret.setResultCode(ResponseUtil.RETURN_CODE_3000);
                        ret.setErrMsg(ResponseUtil.RETURN_MSG_3000);
                        return ret;
                    } else {
                        DateUtils.resetSessionTime(id);
                    }
                }
            }

            Object[] data = null;

            // 該当レコードを取得
            String query = "SELECT rowid, member_id, company_id, rank, sort_order, enable_flag, display_flag, exist_flag, create_date, update_date FROM t_member_favorite WHERE member_id = " + id + " AND company_id = " + code + " ";

            try {
                data = (Object[]) manager.createNativeQuery(query).getSingleResult();
            } catch (NoResultException e) {
                if (rank.equals("0") || rank.equals("1") || rank.equals("2") || rank.equals("3")) {
                    // 新規登録
                    isNew = true;
                } else {
                    ret.setResultCode(ResponseUtil.RETURN_CODE_2001);
                    ret.setErrMsg(ResponseUtil.RETURN_MSG_2001);
                    return ret;
                }
            } catch (Exception ex) {
                ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
                ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
                return ret;
            }

            FavoriteEntity favoriteEntity = new FavoriteEntity();
            if (isNew) {
                favoriteEntity.setMember_id(Integer.parseInt(id));
                favoriteEntity.setCompany_id(Integer.parseInt(code));
                favoriteEntity.setRank(rank);
                favoriteEntity.setSort_order(0);
                favoriteEntity.setEnable_flag(1);
                favoriteEntity.setDisplay_flag(1);
                favoriteEntity.setExist_flag(1);
                favoriteEntity.setCreate_date(DateUtils.getNowDate());
                favoriteEntity.setUpdate_date(DateUtils.getNowDate());
            } else {
                favoriteEntity.setRowid(((Integer) data[0]).intValue());
                favoriteEntity.setMember_id(((Integer) data[1]).intValue());
                favoriteEntity.setCompany_id(((Integer) data[2]).intValue());
                favoriteEntity.setRank(rank);
                favoriteEntity.setSort_order(((Integer) data[4]).intValue());
                favoriteEntity.setEnable_flag(((Integer) data[5]).intValue());
                favoriteEntity.setDisplay_flag(((Integer) data[6]).intValue());
                favoriteEntity.setExist_flag(((Integer) data[7]).intValue());
                favoriteEntity.setCreate_date((String) data[8]);
                favoriteEntity.setUpdate_date((String) data[9]);
            }

            if (rank.equals("0") || rank.equals("1") || rank.equals("2") || rank.equals("3")) {
                favoriteRepository.save(favoriteEntity);
            } else {
                favoriteRepository.delete(favoriteEntity);
            }
        }

        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);
        return ret;
    }

    /** 業種リスト設定API（本番では使用しない） */
    public IndustrialClassLEntity save(IndustrialClassLEntity industrialClassLEntity) {
        return industrialClassLRepository.save(industrialClassLEntity);
    }

    public IndustrialClassMEntity saveM(IndustrialClassMEntity industrialClassMEntity) {
        return industrialClassMRepository.save(industrialClassMEntity);
    }

    public void delete(Integer company_id) {
        industrialClassLRepository.delete(company_id);
    }

    public IndustrialClassLEntity find(Integer company_id) {
        return industrialClassLRepository.findOne(company_id);
    }

    /** insert Hashed value and solt */
    public BaseResponseEntity insertHashedPassword(InsertPasswordRequest insertPasswordRequest) {
        // 返却オブジェクト
        BaseResponseEntity ret = new BaseResponseEntity();

        // リクエストパラメータを取得
        String loginId = insertPasswordRequest.getLoginId();
        int studentId = insertPasswordRequest.getStudentId();
        String password = insertPasswordRequest.getPassword();

        if (TextUtils.isEmpty(loginId) || TextUtils.isEmpty(password)) {
            ret.setResultCode(ResponseUtil.RETURN_CODE_2101);
            ret.setErrMsg(ResponseUtil.RETURN_MSG_2101);
            return ret;
        }

        // 該当レコードを取得
        UserEntity update = userRepository.findOne(studentId);

        // Login IDからソルト値を生成（SHA-512）
        String saltedId = hashKey(loginId);

        // パスワードにSalt値を足してからハッシュ値を生成（SHA-512）
        String hashedPassword = hashKey(saltedId + password);

        update.setUser_password_salt(saltedId);
        update.setUser_password_hash(hashedPassword);

        userRepository.save(update);

        ret.setResultCode(ResponseUtil.RETURN_CODE_0000);

        return ret;
    }

    /**
     * SHA-512化された文字列を取得します。
     * 
     * @param key
     *            キー
     * @return SHA-512化された文字列
     */
    public static String hashKey(final String key) {
        String cacheKey = null;
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(key.getBytes("UTF-8"));
            cacheKey = TextUtils.bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            return ResponseUtil.RETURN_CODE_3001;
        } catch (UnsupportedEncodingException e) {
            return ResponseUtil.RETURN_CODE_3001;
        }
        return cacheKey;
    }
}