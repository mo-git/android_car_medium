package com.zhidian.app.sdk.http;

/**
 * Created by Alan on 2015/6/10.
 */
public class RequestUrl {
    public static final String LOGIN = "/login";

    public static final String LOGOUT = "/logout";

    public static final String BIND_CHILD = "/bind_child";

    public static final String FETCH_NOTICE = "/fetch_notice";

    public static final String BIND_CARD = "/bind_card";

    public static final String SEND_SMS_CODE = "/send_sms_code";

    public static final String SEND_NOTICE = "/send_notice";

    public static final String GET_NOTICE_RECEIVER_LIST = "/fetch_notice_departments";

    public static final String GET_NOTICE_RECEIVER_STATUS = "/fetch_notice_members";

    public static final String SYNC_CONTACT = "/fetch_contacts";

    public static final String SYNC_DEPARTMENT_MEMBERS = "/fetch_department_members";

    public static final String GET_DEPARTMENT_INFO = "/fetch_departmentinfo";

    public static final String GET_DEPARTMENT_INFO_BY_GROUP_ID = "/fetch_department_by_groupId";

    public static final String GET_USER_INFO = "/fetch_userinfo";

    public static final String FETCH_CHECKIN_RECORD = "/fetch_checkin";

    public static final String GET_CLOUD_STORAGE_TOKEN = "/get_uploadinfo";

    public static final String GET_BIND_CARD = "/fetch_user_card";

    public static final String REPORT_LOST_CARD = "/report_loss_card";

    public static final String MARK_NOTICE_READ = "/read_notice";

    public static final String GET_CHILD = "/fetch_child";

    public static final String ACTIVE_USER = "/active_user";

    public static final String SET_USER_PROFILE = "/save_user_profile";

    public static final String FETCH_USER_PROFILE = "/fetch_user_profile";

    public static final String RESET_PASSWORD = "/set_password";

    public static final String UPDATE_MOBILE = "/update_mobile";

    public static final String UPDATE_BINDING = "/update_bind";

    public static final String UPDATE_USER_INFO = "/update_user_info";

    public static final String CHANGE_PASSWORD = "/update_password";

    public static final String ADD_RELATIVE = "/active_invite_user";

    public static final String REMOVE_RELATIVE = "/relieve_bind";

    public static final String GET_BIND_RELATIVE = "/get_binding_parent_list";

    public static final String FETCH_FEED_MEDICINE_TASK = "/fetch_feed_medicine_task";

    public static final String ADD_FEED_MEDICINE_TASK = "/send_feed_medicine_task";

    public static final String FETCH_GARDEN_MAIL = "/fetch_garden_mail";

    public static final String SEND_GARDEN_MAIL = "/send_garden_mail";

    public static final String SEND_COMMENT = "/send_comment";

    public static final String SHOW_COMMENT = "/show_comments";

    public static final String FETCH_FEED = "/fetch_feed";

    public static final String FETCH_USER_FEED = "/fetch_user_feed";

    public static final String DELETE_FEED = "/delete_feed";

    public static final String PUBLISH_FEED = "/send_feed";

    public static final String DELETE_COMMENT = "/delete_comment";

    public static final String FETCH_COMMENT_TO_ME = "/fetch_comments_to_me";

    public static final String FETCH_POST = "/fetch_post";

    public static final String FETCH_POST_GROUP = "/fetch_postgroup";

    public static final String FETCH_POST_DETAIL = "/fetch_post_detail";

    public static final String FETCH_GARDEN_INTRO = "/fetch_garden_intro";

    public static final String FETCH_AGREEMENT = "/fetch_agreement";

    public static final String UPDATE_COUNTER = "/fetch_counter";

    public static final String UPDATE_DEVICE_TOKEN = "/update_device_token";

    public static final String MARK_MEDICINE_TASK_AS_READ = "/read_feed_medicine";

    public static final String MARK_GARDEN_MAIL_AS_READ = "/read_garden_mail";

    public static final String FETCH_CONCERNED_COMMENT = "/fetch_concerned_comment";

    public static final String UPLOAD_DEBUG_FILE_INFO = "/collect_log";

    public static final String MUTE = "/mute";

    public static final String UNMUTE = "/unmute";

    public static final String FETCH_MUTED_LIST = "/fetch_mute";

    public static final String UPGRADE = "/upgrade";

    public static final String CLEAR_NOTICE = "/clear_notice";

    public static final String DEL_GARDEN_MAIL = "/del_garden_mail";

    public static final String CLEAR_GARDEN_MAIL = "/clear_garden_mail";

    public static final String CLEAR_CHECK_IN = "/clear_check_in";

    public static final String FETCH_CLASS_PICTURE = "/fetch_department_photo";

    public static final String USER_CHECK_IN = "/user_check_in";

    public static final String SCAN_CODE_CHECK_IN = "/scan_code_checkin";

    public static final String FETCH_CHILD_CHECK_IN = "/fetch_attendance";

    public static final String INVITE_USER = "/teacher_invitation_parent";

    public static final String CLASS_ATTENDANCE = "/class_attendance";

    public static final String CHILD_ATTENDANCE = "/child_attendance";

    public static final String UPDATE_ATTENDANCE = "/update_attendance";

    public static final String GET_TEACHER_ATTENDANCE_DAYS = "/teacher_attendance_day";

    public static final String GET_TEACHER_ATTENDANCE_STAT = "/teacher_attendance_month";

    public static final String APPLY_LEAVE = "/apply_leave";

    public static final String TEACHER_APPLY_LEAVE = "/teacher_apply_leave";

    public static final String APPROVE_LEAVE = "/approve_leave";

    public static final String TEACHER_APPROVE_LEAVE = "/teacher_approve_leave";

    public static final String FETCH_LEAVE = "/fetch_leave";

    public static final String GET_TEACHER_LEAVE = "/fetch_teacher_leave";

    public static final String GET_HOLIDAY = "/fetch_rest_day";

    public static final String FETCH_TOP_HOT_QUESTION = "/fetch_hot_questions";

    public static final String FETCH_QUESTION = "/fetch_questions";

    public static final String ASK_QUESTION = "/ask_question";

    public static final String GET_QUESTION_TAGS = "/fetch_tags";

    public static final String ANSWER_QUESTION = "/answer_question";

    public static final String GET_ANSWER_AND_QUESTION = "/fetch_personal_questions_answers";

    public static final String GET_ANSWERS = "/fetch_question_answers";

    public static final String DELETE_ANSWERS = "/delete_question_answer";

    public static final String GET_QUESTION_DETAIL = "/fetch_question_detail";

    public static final String GET_TOP_HOT_KNOWLEDGE = "/fetch_hot_knowledges";

    public static final String GET_KNOWLEDGE = "/fetch_knowledges";

    public static final String GET_KNOWLEDGE_DETAIL = "/fetch_knowledge_detail";

    public static final String GET_TOP_HOT_EXPORTS = "/fetch_recommend_experts";

    public static final String GET_EXPORTS = "/fetch_experts";

    public static final String GET_EXPORT_DETAIL = "/fetch_expert";

    public static final String GET_COMMUNION_MESSAGE = "/fetch_communion_message";

    public static final String GET_RESOURCE_BANNER = "/get_resource_banners";

    public static final String GET_RESOURCE_CATEGORY = "/get_resource_category";

    public static final String GET_HOME_PAGE_RESOURCE = "/fetch_home_page_resources";

    public static final String GET_HOT_RESOURCE = "/fetch_hot_resource";

    public static final String GET_RESOURCE = "/fetch_resource";

    public static final String GET_RECOMMENDED_RESOURCE = "/fetch_recommended_resource";

    public static final String GET_PROVIDER_BY_UPDATE_TIME = "/fetch_provider_by_update";

    public static final String GET_PLAY_HISTORY = "/fetch_play_history";

    public static final String GET_ALBUM_BY_CATEGORY = "/fetch_album_by_category";

    public static final String GET_ALBUM_BY_PROVIDER = "/fetch_album_by_provider";

    public static final String PLAY_NEXT = "/play_next";

    public static final String PLAY_RESOURCE = "/play_resource";

    public static final String GET_SEARCH_KEYWORD = "/fetch_search_keywords";

    public static final String GET_SEARCH_RESULT = "/search_resource";

    public static final String GET_RESOURCE_BY_ALBUM = "/fetch_resource_by_album";

    public static final String GET_RESOURCE_PICTURE = "/fetch_resource_pictures";

    public static final String GET_RESOURCE_BY_ID = "/fetch_resource_by_id";

    public static final String GET_PROVIDER_BY_ID = "/fetch_provider_by_id";

    public static final String GET_ALBUM_BY_ID = "/fetch_album_by_id";

    public static final String GET_NEAR_RESOURCE = "/fetch_near_resources";

    public static final String GET_LIGHT_APPS = "/fetch_app";

    public static final String SEARCH_REQUESTION = "/search_question";

    public static final String UPDATE_QUESTION = "/update_question";

    public static final String DELETE_QUESTION = "/delete_question";

    public static final String GET_QUESTION_BANNER = "/fetch_question_banner";

    public static final String INCR_QUESTION_BANNER = "/incr_question_banner_hits";

    public static final String GET_LIVENESS = "/principal_liveness";

    public static final String GET_CLASSACTIVE = "/principal_clasactive";

    public static final String GET_GARDEN_ACTIVE = "/principal_gardenactive";

    public static final String GET_TEACHER_ATTENDANCE = "/garden_attendance";

    public static final String GET_GARDEN_ATTENDANCE = "/principal_weekattendance";

    public static final String GET_SENTIMENTWORD_LIST = "/principal_sentimentword";

    public static final String SAVE_SENTIMENTWORD = "/principal_savesentimentword";

    public static final String DEL_SENTIMENTWORD = "/principal_deletesentimentword";

    public static final String GET_SENTIMENT_MSG = "/principal_sentimentmsg";

    public static final String SEND_MSG_2_TEACHER = "/sendMsgToTeacher";

    public static final String IGNORE_SENSITIVE = "/ignore_sensitive";

    public static final String SENDNOTICE_MSG = "/principal_sendnotice";

    public static final String GET_COURSE_LIST = "/fetch_course_list";

    public static final String GET_COURSE = "/fetch_course";

    public static final String GET_COURSELESSON = "/fetch_course_lesson";

    public static final String GET_COURSECOMMENT_LIST = "/fetch_course_comment";

    public static final String ADD_COURSECOMMENT = "/add_course_comment";

    public static final String MODIFY_COURSECOMMENT = "/edit_course_comment";

    public static final String GET_COURSECOMMENT = "/fetch_user_course_comment";

    public static final String GET_COURSELESSON_LIST = "/fetch_course_lesson_list";

    public static final String PLAY_COURSE = "/play_course_lesson";

    public static final String GET_SUBCRIPTION_COURSE = "/fetch_subcription_course";

    public static final String GET_ARTICLE_LIST = "/article/fetchArticleList";

    public static final String GET_KEYWORDS_LIST = "/article/fetchKeywords";

    public static final String SEARCH_ARTICLE = "/article/searchArticle";

    public static final String ARTICLE_FEED = "/article/fetchArticlePost";

    public static final String GET_SHARE_INFO = "/article/fetchShareInfo";

    public static final String GET_GROWLIST = "/brand/grow/getGrowList";

    public static final String GET_GROW_COMMENT_LIST = "/brand/grow/getGrowComment";

    public static final String ADD_GROW = "/brand/grow/addMediaInfo";

    public static final String ADD_COMMENT = "/brand/grow/addGrowComment";

    public static final String DELETE_GROW = "/brand/grow/deleteBrandGrow";

    public static final String DELETE_GROW_COMMENT = "/brand/grow/deleteGrowComment";

    public static final String GET_GROWTIME_PHOTO = "/brand/grow/getTimePhoto";

    public static final String GET_SYNC_STATES = "/brand/sync/getSyncStatus";

    public static final String SYNC_FEED = "/brand/sync/syncFeed";

    public static final String GET_SYNC_FEED_PROGRESS = "/brand/sync/getSyncProc";

    public static final String GET_CLASSPHTOT_STATE = "/brand/sync/isClassPhotoUpdate";

    public static final String IS_CLASSPHOTOT_SYNC = "/brand/sync/isClassPhotoSynced";

    public static final String GET_CHILDCLASS_PHTOT = "/brand/sync/getChildClassPhoto";

    public static final String SYNC_CLASSPHOTOS = "/brand/sync/syncClassesPhoto";

    public static final String EDIT_MEDIAINFO = "/brand/grow/editMediaInfo";

    public static final String DELETE_MEDIAINFO = "/brand/grow/deleteMediaInfo";

    public static final String GET_ATTENDANCE_BABY = "/attendance_baby";

    public static final String GET_ACTIVE_RANKING_BABY = "/active_ranking";

    public static final String GET_LEVEL_RANKING_BABY = "/level_ranking";

    public static final String GET_CLASS_RANKING_BABY = "/class_ranking";

    public static final String GET_FETCH_REWARD = "/fetch_reward_List";

    public static final String GET_REWARD = "/get_reward";

    public static final String UPLOAD_CLASS_PHOTO = "/upload_class_photo";

    public static final String DEL_CLASS_PHOTO = "/del_class_photo";

    public static final String SET_FEED_BACKGROUND_IMAGE = "/set_feed_img";

    public static final String SET_LAST_FETCHTIEM = "/set_fetch_time";

    public static final String GET_RECOMMAND_LIST = "/subscriptionAccount/recommentList";

    public static final String GET_MY_SUBSCRIPTION_LIST = "/subscriptionAccount/myList";

    public static final String GET_SUBSCRIPTION_DETAIL = "/subscriptionAccount/detail";

    public static final String FOLLOW_SUBSCRIPTION = "/subscriptionAccount/concern";

    public static final String GET_SUBSCRIPTION_KEYWORLDS_LIST = "/subscriptionAccount/keywordList";

    public static final String SEARCH_SUBSCRIPTION = "/subscriptionAccount/search";

    public static final String SEARCH_MY_SUBSCRIPTION = "/subscriptionAccount/searchMy";

    public static final String GET_SUBSCRIPTION_LIST = "/subscriptionAccount/article/list";

    public static final String GET_SUBSCRIPTION_GROUP = "/subscriptionAccount/article/group";

    public static final String SEARCH_SUBSCRIPTION_BY_KW = "/subscriptionAccount/article/search";

    public static final String CLEAR_NO_READ_COUNT = "/subscriptionAccount/clearNoreading";

    public static final String GET_SUBSCRIPTION_ARTICLE_COMMENTLIST = "/subscriptionAccount/fetch_article_comments";

    public static final String ADD_SUBSCRIPTION_COMMENT = "/subscriptionAccount/comment_article";

    public static final String REGISTER = "/register";

    public static final String UPDATE_CHILD = "/update_child";

    public static final String DELETE_CHILD = "/delete_child";

    public static final String ADD_CHILD = "/add_child";

    public static final String FETCH_TODO = "/fetch_todo";

    public static final String ARGEE_ENTER_SCHOOL = "/agree_enter_school";

    public static final String AGREE_INVITED = "/agree_invite";

    public static final String CONFIRM_ENTER_SCHOOL = "/confirm_enter_school";

    public static final String REFUSE_TODO = "/refuse_todo";

    public static final String INVITE_PARENT = "/invite_parent";

    public static final String ACCUSATION = "/accusation";

    public static final String UPGRADE_PATCH = "/upgrade_patch";

    public static final String UPGRADE_INCREMENT = "/upgrade_increment";

    public static final String SHARE_GET_SCORE = "/share_get_score";

    public static final String GET_GARDEN_LIST = "/fetch_garden_list";

    public static final String CHANGE_GARDENID = "/change_gardenId";

    public static final String GROUP_ATTENDANCE = "/group_attendance";

    public static final String GROUP_ACTIVE = "/group_active";

    public static final String GROUP_LIVENESS = "/group_liveness";

    public static final String GET_HEADLINE = "/fetch_headline_list";

    public static final String GET_COURSE_BANNER = "/fetch_course_banner";

    public static final String GET_COURSE_TYPE = "/fetch_course_type";

    public static final String GET_HOME_COURSE = "/fetch_courseinfo";

    public static final String GET_COURSE_INFO = "/fetch_course_package";

    public static final String GET_HAVING_COURSE = "/fetch_having_course";

    public static final String CLICK_BANNNER = "/course_click_bannner";

    public static final String GET_PAY_INFO = "/admin/course_pay";
}
