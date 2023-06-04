# USER_GUI

GUI for Graduation Project of Android app USER
![image](https://github.com/Gachon-USER/USER_GUI_AOS/blob/main/temp_1669193441968.554083062.jpeg?raw=true)
# USER application GUI

### layout
![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/1a3ae0e6-a37e-4b33-857b-21f04bbaee35)  
layout은 사용자에게 표시되는 화면 입니다. 앱 사용 중 보게 되는 여러가지 화면이 xml파일로 저장되어 있습니다. 

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/77f5e109-aef3-48c0-b033-a8c2ecfb12a8)   
layout의 xml파일에서, 버튼, 이미지, 텍스트 같은 다양한 요소들을 담고 있는 틀이 필요합니다. 여러 틀의 종류 중에서 <linearlayout>은 화면의 요소들을 가로, 혹은 세로 방향으로 쌓아서 배치 시켜줍니다. 

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/c3b64e95-082b-4cc3-a1bc-23d3d3432db4)  
layout안으로 들어가는 여러가지 요소 중에서, 버튼의 예시를 들어보겠습니다. <ImageButton>은 버튼에 이미지가 들어가 있는 형태의 버튼으로, 버튼의 사이즈, 위치, 사진의 종류, 식별 가능하도록 하는 고유 ID등을 정의하여 줍니다


### font
![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/74617384-62e7-461f-8bee-dad973188557)  
앱에 들어가게 되는 다양한 폰트를 사용하기 위해서는 폰트 파일을 폴더에 저장하고, xml파일을 이용하여 불러오는 파일이 따로 필요합니다. 

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/641ed09d-d825-4d1c-9694-272dae8aec27)  
다음과 같이 폰트의 이름을 설정하고, 저정된 폰트 파일(.ttf)을 불러오고, 폰트의 기본 두께, 크기 등을 지정하여 줍니다. 

### drawable
![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/eacabcd5-eca4-4773-8d22-663efd564255)    
drawable은 앱에 필요한 다양한 이미지 뿐 아니라 버튼의 모양을 정의하는 xml파일, 혹은 구분선의 모양을 정의하는 xml파일 등 이미지 요소들을 저장하고 있습니다. 

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/613f0e90-724e-4d90-a8a7-25547fc77aea)  
버튼의 속성을 정의하는 xml파일 입니다. 버튼의 색, 선 색, 선의 두께, 버튼을 눌렀을떄의 효과 등을 정의해줍니다. 


### Java
![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/1979299d-3d97-4dde-8ca8-8890f335f165)   
사용자에게 보여지는 부분은 xml파일로 정의하고, 그 내부에 요소들이 어떻게 동작하게 되는지는 java파일에서 정의합니다. Java파일은 어떤 layout을 보여줄지 정의하며, 이후 다른 java파일로 넘겨지거나 특정 작업을 수행하도록 해줍니다. 



***

## Log In Page

### 로그인 화면

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/e8eb654a-cbc2-4c27-b723-0e326819b90f)    
아이디와 비밀번호를 입력하고, 신규 회원인 경우에는 회원가입 하는 페이지로 넘어가도록 합니다. 

## Sign Up Page

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/65f55c51-ada8-45aa-af05-25cbb9acfd6d)   
이메일과 비밀번호를 입력받고, 두 비밀번호가 같은지 확인합니다. 회원가입이 완료되면, 다음 서버에 제출하고, 메인으로 넘어가도록 해주며, 혹시 로그인 과정에서 계정이 있는데 잘못 눌러서 회원가입 부분으로 들어간 경우, 다시 로그인 페이지로 돌아가도록 해줍니다. 


## Main Page

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/29b0f551-240e-4013-99ab-e1e424025fb5)  
로그인 후 나오는 메인 화면. 상단 바에는 검색을 할 수 있도록 하고, 아래 부분에는 각각의 페이지로 넘어갈 수 있는 버튼을 만들어 줍니다. 또한 바로 레시피 리스트를 확인할 수 있도록, 카테고리 별로 확인할 수 있는 바로 가기, 그 밑으로는 사용자의 이전 사용 기록을 토대로한 추천 레시피, 마지막으로는 최근에 사용한 레시피를 바로가기를 보여주게 됩니다. 

## Recipe List Page

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/a7c76cdf-f037-4993-b425-3012a28b6f8b)  
음식 리스트를 확인할 수 있는 화면. 앞서 메인 페이지에서 상단의 카테고리별 바로 가기를 누르거나, 검색을 하거나 따라서 레시피의 리스트를 확인할 수 있습니다. 

## Recipe View Page

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/ff202088-b8e9-4388-9b2c-378ca0ca5f95)   
레시피를 확인하는 부분으로, 왼쪽에는 사진, 오른쪽에는 재료가 나오고, 음성인식 시작 버튼을 통해서 음성안내를 시작할 수 있습니다. 그리고 조리법이 나와 레시피를 확인할 수 있습니다. 

## User Info Page

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/20e3ee4c-011f-4751-942b-f13578e4691d)   
자신의 정보를 확인할 수 있는 페이지. 유저 이름, 지금까지 모은 포인트, 등록된 레시피를 확인할 수 있고, 밑에 부분에는 자신이 등록한 레시피의 리스트에서 수정할 수 있습니다.  

## Recipe Enroll Page

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/2a157525-e509-4095-ba59-1e8af7358341)   
레시피를 등록하는 부분으로, 사진 업로드. 재료 등록, 조리법 등록, 카테고리 등록을 진행하게 됩니다. 

## Voice Control Page

![image](https://github.com/Gachon-USER/Gachon_USER_Project_Main/assets/93314745/b54790fd-a1f3-4f48-8d42-d1baf80f62b0)   
음성안내 부분, 레시피를 읽어주거나 타이머 세팅, 이전 명령어 반복, 명령어 반복 등과 같은 다양한 명령어를 음성으로 처리할 수 있도록 해줍니다. 
