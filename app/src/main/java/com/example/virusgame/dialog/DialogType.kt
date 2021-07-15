package com.example.virusgame.dialog

/**
 * @since 2021.02.08.Tue
 * @author Thomas Park in ROWAN INC.
 */
enum class DialogType {

    SINGLE_BUTTON,

    /** 텍스트 하나, 버튼 하나 다이얼로그 */
    DUAL_BUTTON,

    /** 텍스트 하나, 버튼 둘 다이얼로그 */
    TYPE_FIND_TREASURE,
    TYPE_FIND_ACORN,
    TYPE_MERGE_GEOMETRY,
    TYPE_NETWORK,

    /** 텍스트 둘, 버튼 하나 다이얼로그 */
    YES_NO,

    /** 게임 디자인 다이얼로그, 텍스트 하나, 버튼 둘 다이얼로그 */

    YES_NO_GAME,

    /** 게임 디자인 다이얼로그, 텍스트 하나, 버튼 둘 다이얼로그 */
    YES_NO_VIDEO,

    /** 비디오 디자인 다이얼로그, 텍스트 둘, 버튼 둘 다이얼로그 */
    NO_BTN_SINGLE_TEXT,

    /** 버튼 없고, 텍스트 하나만 */
    SINGLE_TITLE_DESCRIPTION_BUTTON,

    /**  인지 과제 결과 */
    COGNITION_RESULT,

    /** 메달 선택 */
    SELECT_MEDAL,

    /** 게임 OX */
    GAME_OX,

    /** 게임 과제 결과 */
    GAME_RESULT,

    /** 영상이어보기 */
    FOLLOW_UP_VIDEO,

    /**  인지 과제 결과 */
    COGNITION_RESULT_V2,

    /** 게임 과제 결과 */
    GAME_RESULT_V2,

    /** 회상 과제 결과 */
    REMINISCENCE_RESULT,

    /** 게임 종료 메뉴 */
    GAME_CLOSE,

    /** 퀴즈 다시 풀어보세요 */
    QUIZ_RETRY,

    /** 퀴즈 정답입니다 */
    QUIZ_CORRECT,

    /** 퀴즈 오답입니다 */
    QUIZ_FAILED,

    /** 퀴즈 정답화인 */
    QUIZ_CHECK,

    /** 회상카테고리 정답만 */
    QUIZ_REMINISCENCE,

    /** 회상카테고리 정답 + 설명 */
    QUIZ_REMINISCENCE_LENGTHY
}