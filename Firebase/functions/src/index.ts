import * as functions from 'firebase-functions';

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// [END functionsimport]
// [START additionalimports]
// Moments library to format dates.
var admin = require("firebase-admin");
var serviceAccount = require("./wollysession.json");

// Initialize the app with a service account, granting admin privileges
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://wolly-1569687603282.firebaseio.com/"
});

// As an admin, the app has access to read and write all data, regardless of Security Rules
var db = admin.database();
// CORS Express middleware to enable CORS Requests.
const cors = require('cors')({
    origin: true,
});


export const helloWorld = functions.https.onRequest((request, response) => {
    response.send("Hello from Firebase!");
});
exports.receivekgdelta = functions.https.onRequest((req, res) => {
    if (req.method === 'PUT') {
        return res.status(403).send('Forbidden!');
    }
    // [END sendError]

    // [START usingMiddleware]
    // Enable CORS using the `cors` express middleware.
    //
    return cors(req, res, () => {
        const reference = db.ref("User/");
        const unsetKg = reference.child("unsetKg");
        const weightDelta : number = req.body.weightdelta;
        unsetKg.once("value", function (data) {
            const newValue : number = +Number(data.val()) + +Number(weightDelta);
            unsetKg.set(newValue);
            console.log('Set value to ', newValue);
            res.status(200).send("Value changed to " + newValue);
        });

    });
});

/*exports.test = functions.https.onRequest((req, res) => {
    if (req.method === 'PUT') {
        return res.status(403).send('Forbidden!');
    }
    // [END sendError]

    // [START usingMiddleware]
    // Enable CORS using the `cors` express middleware.
    return cors(req, res, () => {
        // [END usingMiddleware]*/
        // Reading date format from URL query parameter.
        // [START readQueryParam]
        /*const BOTTLE = 0;
        const BOTTLEPOINTSUNIT = 5.0;

        const PAPER = 1;
        const PAPERPOINTSUNIT = 1.0;
        const PAPERPOINTSKG = 200.0;

        const CAP = 2;
        const CAPPOINTSUNIT = 1.1;
        const CAPPOINTSKG = 220.0;

        const TETRAPAK = 3;
        const TETRAPAKPOINTSUNIT = 5.0;

        const GLASSBOTTLE = 4;
        const GLASSBOTTLEPOINTSUNIT = 9.0;

        const BATTERY = 5;
        const BATTERYPOINTSUNIT = 6.0;


        const origin = req.query.origin;
        /*const type = req.body.type;
        const isWeight = req.body.isWeight;
        let points = 0;
        if( isWeight ){
            const kgChange = req.body.kgChange;
            points = kgChange;
            if(type === CAP ){
                points *= CAPPOINTSKG;
            }else if (type === PAPER){
                points *= PAPERPOINTSKG
            }

        }else{
            const kgChange = req.body.kgChange;

        }
        // [END readQueryParam]
        // Reading date format from request body query parameter

        // [START sendResponse]
        const formattedDate = moment().format(format);
        console.log('Sending Formatted date:', formattedDate);
        res.status(200).send(formattedDate);
        // [END sendResponse]*/
