import firebase_admin
from firebase_admin import credentials,firestore
from firebase_admin import storage
cred = credentials.Certificate("./adminKey.json")
firebase_admin.initialize_app(cred, {
    'storageBucket': 'women-e598c.appspot.com'
})
#Database Methods
db = firestore.client()

#discrip = ""


title = "Plight of Women"
cloudStorageLink = "https://firebasestorage.googleapis.com/v0/b/women-e598c.appspot.com/o/y2mate.com%20-%20The%20plight%20of%20women%20in%20India_360p.mp4?alt=media&token=3633254b-9fee-4f0c-9fa3-057e0616545c"
name = "CNN"
source = "YouTube"
sourceLink = "https://www.youtube.com/watch?v=XtHgTf67hzc"
discription = "CNN's Sumnima Udas examines the cycle of discrimination against women in India."
viewsOnVideo = 637,57,144340
socialHandle = " "
webpage = ""

if(len(title)!=0 and len(cloudStorageLink)!=0):
    videsoWrite =  db.collection("adminContent").document("Videos").collection("data").document().set({
        "title":title,
        "name":name,
        "source":source,
        "sourceLink":sourceLink,
        "discription":discription,
        "viewsOnVideo":viewsOnVideo,
        "socialHandle":socialHandle,
        "webpage":webpage,
        "cloudStorageLink":cloudStorageLink
    }) 
else:
    print("Error")
